package co.paulfran.paulfranco.inventoryappversion2;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);
        TextView qtySummaryTextView = (TextView) view.findViewById(R.id.qty);
        TextView saleTextView = (TextView) view.findViewById(R.id.saleTextView);

        // Find the columns of product attributes that we're interested in
        int idColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME);
        int manufacturerColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_MANUFACTURER);
        int qtyColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_QUANTITY);

        // Read the pet attributes from the Cursor for the current product
        final int productId = cursor.getInt(idColumnIndex);
        String productName = cursor.getString(nameColumnIndex);
        String productManufacturer = cursor.getString(manufacturerColumnIndex);
        final int productQty = cursor.getInt(qtyColumnIndex);


        // Update the TextViews with the attributes for the current product
        nameTextView.setText(productName);
        summaryTextView.setText(context.getString(R.string.manufacturer) + productManufacturer);
        qtySummaryTextView.setText(context.getString(R.string.in_stock) + productQty);

        // on click listener on the sale text view
        saleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri currentProductUri = ContentUris.withAppendedId(ProductContract.ProductEntry.CONTENT_URI, productId);
                productSale(context, productQty, currentProductUri);
            }
        });
    }
    // reduce qty on click of sale text view
    private void productSale(Context context, int productQty, Uri uriProduct) {
        if (productQty == 0) {
            Toast.makeText(context, "There are 0 available", Toast.LENGTH_SHORT).show();
        } else {
            int newProductQty = productQty - 1;
            ContentValues values = new ContentValues();
            values.put(ProductContract.ProductEntry.COLUMN_QUANTITY, newProductQty);
            context.getContentResolver().update(uriProduct, values, null, null);
        }
    }

}