package pos.machine;

import java.util.List;
import java.util.stream.Collectors;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<String> distinctBarcodes = getDistinctBarcodes(barcodes);

        return getDisplayReceipt(distinctBarcodes, barcodes);
    }

    private List<String> getDistinctBarcodes(List<String> barcodes) {
        return barcodes.stream().distinct().collect(Collectors.toList());
    }

    private String getDisplayReceipt(List<String> distinctBarcodes, List<String> barcodes) {
        List<ItemInfo> allItemInfo = ItemDataLoader.loadAllItemInfos();
        StringBuilder result = new StringBuilder();
        result.append(getHeader());
        int totalPrice = 0;
        for (String barcode : distinctBarcodes) {
            ItemInfo itemInfo = getItemInfo(barcode, allItemInfo);
            ItemSummary itemSummary = new ItemSummary(itemInfo);
            itemSummary.countItem(barcodes);
            result.append(itemSummary.getItemSummaryLine());
            totalPrice += itemSummary.getSubTotal();
        }
        result.append("----------------------\n")
                .append("Total: ").append(totalPrice).append(" (yuan)\n")
                .append("**********************");
        return result.toString();
    }

    private String getHeader(){
        return "***<store earning no money>Receipt***\n";
    }

    private ItemInfo getItemInfo(String barcode, List<ItemInfo> allItemInfo) {//-> stream for enhancement
        ItemInfo result = null;

        for (ItemInfo itemInfo : allItemInfo) {
            if (itemInfo.getBarcode().equals(barcode)) {
                result = itemInfo;
            }
        }
        return result;
    }
}
