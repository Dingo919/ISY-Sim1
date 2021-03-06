/*
 * The MIT License
 *
 * Copyright 2019 gfoster.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package sim.Layers;

import javafx.scene.canvas.GraphicsContext;
import sim.Objects.WasteDischargeObject;
import sim.Objects.WasteSourceObject;
import sim.Utilities.Posn;

/**
 *
 * @author gfoster
 */
public class WasteSourceLayer extends MatrixLayer {


    protected static WasteSourceLayer instance = null;
    protected int cellWidth;

    public static WasteSourceLayer getWasteSourceLayer(GraphicsContext gContext
                                                      ,double width
                                                      ,double height
                                                      ,double horizScale
                                                      ,double vertScale
                                                      ,int cellWidth
                                                      ) {
    	if (instance == null) {
            instance = new WasteSourceLayer(gContext
                                           ,width
                                           ,height
                                           ,horizScale
                                           ,vertScale
                                           ,cellWidth
                                           );
    	}
    	return instance;
    } // end Singleton getWasteSourceLayer
    
    // This method should only be used if the layer has been created
    // If the layer has not be created it will throw an exception
    public static WasteSourceLayer getWasteSourceLayer() {
    	if (instance == null) {
    		throw new NullPointerException("WasteSourceLayer not instansiated");
    	}
    	return instance;
    } // end Singleton getWasteSourceLayer

    private WasteSourceLayer(GraphicsContext gContext
                            ,double width
                            ,double height
                            ,double horizScale
                            ,double vertScale
                            ,int cellWidth
                            ) {
        super(gContext, width, height, horizScale, vertScale, cellWidth);
        this.cellWidth = cellWidth;
    } // end private constructor

    public int generate(LandLayer ll, WasteLayer wl){
        int items = 0;
        for (int i = 0; i < m.matrix.length; i++) {
            for (int j = 0; j < m.matrix[i].length; j++) {
            	if (m.matrix[i][j]!=null) {
                    Posn ps = toScreenCoordiantes(i,j);
                    Posn pl = ll.toLayerCoordinates(ps);
                    Posn posn = ll.getVacantArea(pl.getX(), pl.getY());
                    if (posn != null){
                        WasteSourceObject wso = (WasteSourceObject)m.matrix[i][j];
                        WasteDischargeObject wdo = new WasteDischargeObject(wso.getSize(), wso);
                        ps = ll.toScreenCoordiantes(posn);
                        wl.addWaste((int)ps.getX(), (int)ps.getY(), wdo);
                        items ++;
                    }
            	} // end cell exists in matrix 
            } // end inner loop
        } // end outer loop
        return items;
    } // end method generate
    
    public int cellWidth() {
        return cellWidth;
    }

} // end of class WasteSourceLayer
