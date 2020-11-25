package pos.machine;

import java.util.List;
import java.util.stream.Collectors;

public class Receipt {
    private final List<String> barcodes;
    private Integer totalPrice;

    public Receipt(List<String> barcodes) {
        this.barcodes = barcodes;
        this.totalPrice = 0;
    }

    public String getReceiptSummary() {
        List<ItemInfo> allItemInfo = ItemDataLoader.loadAllItemInfos();
        StringBuilder result = new StringBuilder();
        result.append(getHeader());
        for (String barcode : getDistinctBarcodes()) {
            ItemInfo itemInfo = getItemInfo(barcode, allItemInfo);
            ItemSummary itemSummary = new ItemSummary(itemInfo);
            itemSummary.countItem(barcodes);
            result.append(itemSummary.getItemSummaryLine());
            totalPrice += itemSummary.getSubTotal();
        }
        result.append(getItemSummaryFooter())
                .append(getTotalLine())
                .append(getFooter());
        return result.toString();
    }

    private String getHeader() {
        return "***<store earning no money>Receipt***\n";
    }

    private String getItemSummaryFooter() {
        return "----------------------\n";
    }

    private String getTotalLine() {
        return "Total: " + totalPrice + " (yuan)\n";
    }

    private String getFooter() {
        return "**********************";
    }

    private List<String> getDistinctBarcodes() {
        return barcodes.stream().distinct().collect(Collectors.toList());
    }

    private ItemInfo getItemInfo(String barcode, List<ItemInfo> allItemInfo) {//-> stream for enhancement
        return allItemInfo.stream().filter(item -> item.getBarcode().equals(barcode)).findFirst().orElse(null);

    }
}
