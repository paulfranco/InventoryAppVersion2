package co.paulfran.paulfranco.inventoryappversion2;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import co.paulfran.paulfranco.inventoryappversion2.data.ProductContract;

public class ProductCursorAdapter extends CursorAdapter {


    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);
        TextView qtySummaryTextView = (TextView) view.findViewById(R.id.qty);

        // Find the columns of product attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME);
        int manufacturerColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_MANUFACTURER);
        int qtyColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_QUANTITY);

        // Read the pet attributes from the Cursor for the current product
        String productName = cursor.getString(nameColumnIndex);
        String productManufacturer = cursor.getString(manufacturerColumnIndex);
        String productQty = cursor.getString(qtyColumnIndex);


        // Update the TextViews with the attributes for the current pet
        nameTextView.setText(productName);
        summaryTextView.setText("Manufacturer: " + productManufacturer);
        qtySummaryTextView.setText("In Stock: " + productQty);
    }
}