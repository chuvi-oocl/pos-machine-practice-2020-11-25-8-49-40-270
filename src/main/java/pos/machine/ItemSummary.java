package pos.machine;

import java.util.List;

public class ItemSummary extends ItemInfo {
    private int count;

    public ItemSummary(ItemInfo itemInfo) {
        super(itemInfo.getBarcode(), itemInfo.getName(), itemInfo.getPrice());
        count = 0;
    }

    public int getSubTotal() {
        return getPrice() * count;
    }

    public void countItem(List<String> barcodes) {
        for (String barcodeIt : barcodes) {
            if (barcodeIt.equals(getBarcode())) {
                count++;
            }
        }
    }

    public String getItemSummaryLine() {
        return "Name: " + getName() + ", Quantity: " + count + ", Unit price: " + getPrice() + " (yuan), Subtotal: " + getSubTotal() + " (yuan)\n";
    }
}
