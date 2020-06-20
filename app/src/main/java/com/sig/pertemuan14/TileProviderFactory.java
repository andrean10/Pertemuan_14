package com.sig.pertemuan14;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class TileProviderFactory {
    private static String TAG = TileProviderFactory.class.getSimpleName();
    
    public static GeoServerTileProvider getWmsTileProvider() {
        final String WMS_FORMAT_STRING =
                "https://localhost:8443/geoserver/Indonesia/wms" +
                        "?service=WMS" +
                        "&version=1.1.0" +
                        "&request=GetMap" +
                        "&layers=Indonesia%3Aprovinsi_table" +
                        "&bbox=%f,%f,%f,%f" +
                        "&width=256" +
                        "&height=256" +
                        "&srs=EPSG:900913" +
                        "&format=image/png" +
                        "&transparent=true";

        GeoServerTileProvider tileProvider = new GeoServerTileProvider(256, 256) {

            @Override
            public synchronized URL getTileUrl(int x, int y, int zoom) {
                double[] bbox = getBoundingBox(x, y, zoom);
                String s = String.format(Locale.US, WMS_FORMAT_STRING, bbox[MINX], bbox[MINY],
                            bbox[MAXX], bbox[MAXY]);
                URL url = null;
                try {
                    url = new URL(s);
                    Log.d(TAG, "getTileUrl: Data diambil");
                } catch (MalformedURLException e) {
                    Log.e(TAG, "getTileUrl: " + e);
                    throw new AssertionError(e);
                }
                return url;
            }
        };
        return tileProvider;
    }
}
