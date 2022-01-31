package bearmaps.proj2c.server.handler.impl;
import static org.junit.Assert.*;

import bearmaps.proj2c.utils.Constants;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class test {
    @Test
    public void utiltest(){
        //System.out.println(RasterAPIHandler.getDepth());
        System.out.println((Constants.ROOT_LRLON-Constants.ROOT_ULLON)/Constants.TILE_SIZE*Constants.SL/Math.pow(2,7));
        System.out.println((-122.24053+122.241632)/100*Constants.SL);
        System.out.println(RasterAPIHandler.getDepth(-122.241632,-122.24053,100));
        System.out.println(Constants.RangeLat);
        System.out.println(Constants.RangeLon);
        System.out.println(Math.abs(-122.2998046875-(-122.255859375)));
        System.out.println(Math.abs(37.8921955472-37.8574989903));
        //int endTileLon = (int) (Math.ceil(Math.abs(-122.2998-(-122.24053))/Math.abs()));
    }
    @Test
    public void negativetest() {
        double ullon = 486.56112179968557;
        double lrlon = 595.0899738318928;
        double ullat = 878.2898243004133;
        double lrlat = 714.1704921685864;
        double w = 805.2327423300775;
        int depth = RasterAPIHandler.getDepth(ullon, lrlon, w);
        double tileSizeX = Constants.RangeLon/Math.pow(2,depth);
        System.out.println("tileSizeX "+ tileSizeX);
        double tileSizeY = Constants.RangeLat/Math.pow(2,depth);
        System.out.println("tileSizeY "+ tileSizeY);
        boolean success = true;
        //edge case
        if (ullon<Constants.ROOT_ULLON) {
            if (lrlon<Constants.ROOT_ULLON) {
                success=false;
            }
            ullon = Constants.ROOT_ULLON;
        }
        if (lrlon>Constants.ROOT_LRLON) {
            if (ullon>Constants.ROOT_LRLON){
                success=false;
            }
            lrlon = Constants.ROOT_LRLON;
        }
        if (ullat>Constants.ROOT_ULLAT) {
            if (lrlat>Constants.ROOT_ULLAT) {
                success=false;
            }
            ullat = Constants.ROOT_ULLAT;
        }
        if (lrlat<Constants.ROOT_LRLAT) {
            if (ullat<Constants.ROOT_LRLAT){
                success=false;
            }
            lrlat = Constants.ROOT_LRLAT;
        }
        // x
        System.out.println(success);
        int startingTileLon = (int) (Math.abs(Constants.ROOT_ULLON-ullon)/tileSizeX);
        double raster_ul_lon = Constants.ROOT_ULLON+startingTileLon*tileSizeX;
        int endTileLon = (int) (Math.ceil(Math.abs(Constants.ROOT_ULLON-lrlon)/tileSizeX));
        double raster_lr_lon = Constants.ROOT_ULLON+endTileLon*tileSizeX;
        //y
        int startingTileLat = (int) (Math.abs(Constants.ROOT_ULLAT-ullat)/tileSizeY);
        double raster_ul_lat = Constants.ROOT_ULLAT-startingTileLat*tileSizeY;
        int endTileLat = (int) (Math.ceil(Math.abs(Constants.ROOT_ULLAT-lrlat)/tileSizeY));
        double raster_lr_lat = Constants.ROOT_ULLAT-endTileLat*tileSizeY;
        System.out.println("endTileLat "+endTileLat);
        System.out.println("startingTileLat "+startingTileLat);
        System.out.println("endTileLon "+endTileLon);
        System.out.println("startingTileLon "+startingTileLon);
        int colSize = endTileLat-startingTileLat;
        int rowSize = endTileLon-startingTileLon;
        System.out.println(colSize);
        System.out.println(rowSize);
        String[][] a = new String[Math.abs(colSize)][Math.abs(rowSize)];
    }
}
