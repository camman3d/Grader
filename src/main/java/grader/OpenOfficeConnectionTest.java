package grader;
import ooo.connector.BootstrapSocketConnector;

import com.sun.star.awt.Point;
import com.sun.star.awt.Size;
import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;
import com.sun.star.drawing.XDrawPage;
import com.sun.star.drawing.XShape;
import com.sun.star.drawing.XShapes;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.io.IOException;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.presentation.XPresentationPage;
import com.sun.star.uno.AnyConverter;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;


public class OpenOfficeConnectionTest {
	public static String oooExeFolder = "C:/Program Files/OpenOffice.org 3/program/";

    public static void main(String[] args) {
	try {
	    // get the remote office component context
//	    com.sun.star.uno.XComponentContext xContext = com.sun.star.comp.helper.Bootstrap.bootstrap();
		
		XComponentContext xRemoteContext = BootstrapSocketConnector.bootstrap(oooExeFolder);

	    System.out.println("Connected to a running office ...");
 
	    com.sun.star.lang.XMultiComponentFactory xRemoteServiceManager = xRemoteContext.getServiceManager();
	
 
            String available = (xRemoteServiceManager != null ? "available" : "not available");
	    System.out.println("remote ServiceManager is " + available);
	    
	    Object desktop = xRemoteServiceManager.createInstanceWithContext(
                "com.sun.star.frame.Desktop", xRemoteContext);
	    XComponentLoader xCLoader = ( XComponentLoader ) UnoRuntime.queryInterface(XComponentLoader.class, desktop);
//	    XComponent xComponent = drawOrganigram(xRemoteServiceManager, xRemoteContext);
	    presentOrganigram(xRemoteServiceManager, xRemoteContext);
//	    storeComponent(xComponent);
	    
	   
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    System.exit(0);
	}
    }
    public static XComponent drawOrganigram(
    		XMultiComponentFactory xRemoteServiceManager, 
    		XComponentContext xRemoteContext
    		) throws Exception {

        Object desktop = xRemoteServiceManager.createInstanceWithContext(
            "com.sun.star.frame.Desktop", xRemoteContext);
        XComponentLoader xComponentLoader = (XComponentLoader)UnoRuntime.queryInterface(
            XComponentLoader.class, desktop);
        
//        if (isDrawing) {
            PropertyValue[] loadProps = new PropertyValue[0];

        		
            XComponent xDrawComponent = 
	xComponentLoader.loadComponentFromURL(
            "private:factory/sdraw", "_blank", 0, loadProps);
//        } else {
//        	PropertyValue[] loadProps = new PropertyValue[1];
//        	  loadProps[0] = new PropertyValue();
//        	  loadProps[0].Name = "Silent";
//        	  loadProps[0].Value = new Boolean(true);
//        	 xComponentLoader.loadComponentFromURL(
//        	      "private:factory/simpress", "_blank", 0, loadProps);
//        	
//        }
        
        // get draw page by index
        com.sun.star.drawing.XDrawPagesSupplier xDrawPagesSupplier =
            (com.sun.star.drawing.XDrawPagesSupplier)
                UnoRuntime.queryInterface(
        com.sun.star.drawing.XDrawPagesSupplier.class, xDrawComponent );
        com.sun.star.drawing.XDrawPages xDrawPages = xDrawPagesSupplier.getDrawPages();
        Object drawPage = xDrawPages.getByIndex(0);
        com.sun.star.drawing.XDrawPage xDrawPage = (com.sun.star.drawing.XDrawPage)
            UnoRuntime.queryInterface(
                com.sun.star.drawing.XDrawPage.class, drawPage);
        
        drawOrganigram(xDrawComponent, xDrawPage);
        
//        // find out page dimensions
//        com.sun.star.beans.XPropertySet xPageProps = (com.sun.star.beans.XPropertySet)
//            UnoRuntime.queryInterface(
//                com.sun.star.beans.XPropertySet.class, xDrawPage);
//        int pageWidth = AnyConverter.toInt(xPageProps.getPropertyValue("Width"));
//        int pageHeight = AnyConverter.toInt(xPageProps.getPropertyValue("Height"));
//        int pageBorderTop = AnyConverter.toInt(xPageProps.getPropertyValue("BorderTop"));
//        int pageBorderLeft = AnyConverter.toInt(xPageProps.getPropertyValue("BorderLeft"));
//        int pageBorderRight = AnyConverter.toInt(xPageProps.getPropertyValue("BorderRight"));
//        int drawWidth = pageWidth - pageBorderLeft - pageBorderRight;
//        int horCenter = pageBorderLeft + drawWidth / 2;
//        
//        // data for organigram
//        String[][] orgUnits = new String[2][4];
//        orgUnits[0][0] = "Management"; // level 0
//        orgUnits[1][0] = "Production"; // level 1
//        orgUnits[1][1] = "Purchasing"; // level 1
//        orgUnits[1][2] = "IT Services"; // level 1
//        orgUnits[1][3] = "Sales"; // level 1
//        int[] levelCount = {1, 4};
//        
//        // calculate shape sizes and positions
//        int horSpace = 300;
//        int verSpace = 3000;
//        int shapeWidth = (drawWidth - (levelCount[1] - 1) * horSpace) / levelCount[1];
//        int shapeHeight = pageHeight / 20;
//        int shapeX = pageWidth / 2 - shapeWidth / 2;
//        int levelY = 0;
//        com.sun.star.drawing.XShape xStartShape = null;
//        // get document factory
//        com.sun.star.lang.XMultiServiceFactory xDocumentFactory = (com.sun.star.lang.XMultiServiceFactory)
//            UnoRuntime.queryInterface(
//                com.sun.star.lang.XMultiServiceFactory.class, xDrawComponent);
//        // creating and adding RectangleShapes and Connectors 
//        for (int level = 0; level <= 1; level++) {
//            levelY = pageBorderTop + 2000 + level * (shapeHeight + verSpace);
//            for (int i = levelCount[level] - 1; i > -1; i--) {
//                shapeX = horCenter - 
//                         (levelCount[level] * shapeWidth + (levelCount[level] - 1) * horSpace) / 2 + 
//                         i * shapeWidth + i * horSpace;
//                Object shape = xDocumentFactory.createInstance("com.sun.star.drawing.RectangleShape");
//                com.sun.star.drawing.XShape xShape = (com.sun.star.drawing.XShape)
//                    UnoRuntime.queryInterface(
//                        com.sun.star.drawing.XShape.class, shape);
//                xShape.setPosition(new com.sun.star.awt.Point(shapeX, levelY));
//                xShape.setSize(new com.sun.star.awt.Size(shapeWidth, shapeHeight)); 
//                xDrawPage.add(xShape);
//                
//                // set the text
//                com.sun.star.text.XText xText = (com.sun.star.text.XText)
//                    UnoRuntime.queryInterface(
//                        com.sun.star.text.XText.class, xShape);
//                xText.setString(orgUnits[level][i]); 
//                // memorize the root shape, for connectors
//                if (level == 0 && i == 0)
//                    xStartShape = xShape; 
//                
//                // add connectors for level 1
//                if (level == 1) {
//                    Object connector = xDocumentFactory.createInstance(
//                        "com.sun.star.drawing.ConnectorShape");
//                    com.sun.star.drawing.XShape xConnector = (com.sun.star.drawing.XShape)
//                        UnoRuntime.queryInterface(
//                    com.sun.star.drawing.XShape.class, connector); 
//                    xDrawPage.add(xConnector);
//                    com.sun.star.beans.XPropertySet xConnectorProps = (com.sun.star.beans.XPropertySet)
//                        UnoRuntime.queryInterface(
//                            com.sun.star.beans.XPropertySet.class, connector);
//                    xConnectorProps.setPropertyValue("StartShape", xStartShape);
//                    xConnectorProps.setPropertyValue("EndShape", xShape);
//                    // glue point positions: 0=top 1=left 2=bottom 3=right
//                    xConnectorProps.setPropertyValue("StartGluePointIndex", new Integer(2)); 
//                    xConnectorProps.setPropertyValue("EndGluePointIndex", new Integer(0)); 
//                }
//      
//            }
//        }
        
        return xDrawComponent;
        
        
    }
    public static XComponent presentOrganigram(
    		XMultiComponentFactory xRemoteServiceManager, 
    		XComponentContext xRemoteContext
    		) throws Exception {

    	  Object desktop =xRemoteServiceManager.createInstanceWithContext(
    	      "com.sun.star.frame.Desktop", xRemoteContext);
    	 
    	  // query the XComponentLoader interface from the Desktop service
    	  XComponentLoader xComponentLoader = (XComponentLoader)UnoRuntime.queryInterface(
    	      XComponentLoader.class, desktop);
    	 
    	  // define load properties according to com.sun.star.document.MediaDescriptor
    	  // the boolean property Silent tells the office to suppress the impress startup wizard
    	  PropertyValue[] loadProps = new PropertyValue[1];
    	  loadProps[0] = new PropertyValue();
    	  loadProps[0].Name = "Silent";
    	  loadProps[0].Value = new Boolean(true); 
    	 
    	  // load
    	 XComponent xDrawComponent = xComponentLoader.loadComponentFromURL(
    	      "private:factory/simpress", "_blank", 0, loadProps);
         com.sun.star.drawing.XDrawPagesSupplier xDrawPagesSupplier =
                 (com.sun.star.drawing.XDrawPagesSupplier)
                     UnoRuntime.queryInterface(
             com.sun.star.drawing.XDrawPagesSupplier.class, xDrawComponent );
             com.sun.star.drawing.XDrawPages xDrawPages = xDrawPagesSupplier.getDrawPages();
             
             Object drawPage = xDrawPages.getByIndex(0);
             
             XPropertySet props = (XPropertySet)UnoRuntime.queryInterface(
                     XPropertySet.class, drawPage);             
             XPresentationPage xPresentationPage = (XPresentationPage)UnoRuntime.queryInterface(
                     XPresentationPage.class, drawPage);
             // find out page dimensions
             com.sun.star.beans.XPropertySet drawPageProps = (com.sun.star.beans.XPropertySet)
                 UnoRuntime.queryInterface(
                     com.sun.star.beans.XPropertySet.class, xPresentationPage);
             drawPageProps.setPropertyValue("Layout", -1);

//             drawPageProps.setPropertyValue("Layout", 0);
//             drawPageProps.setPropertyValue("Layout", 1);
//
//             drawPageProps.setPropertyValue("Layout", 2);
//             drawPageProps.setPropertyValue("Layout", 3);
//             drawPageProps.setPropertyValue("Layout", 5);
//             drawPageProps.setPropertyValue("Layout", 6);
             
             drawOrganigram(xDrawComponent, xPresentationPage);
        return xDrawComponent;
        
        
    }
    public static void drawOrganigram(XComponent xDrawComponent,
    		XDrawPage xDrawPage
    		) throws Exception {

        
        
        // find out page dimensions
        com.sun.star.beans.XPropertySet xPageProps = (com.sun.star.beans.XPropertySet)
            UnoRuntime.queryInterface(
                com.sun.star.beans.XPropertySet.class, xDrawPage);
        int pageWidth = AnyConverter.toInt(xPageProps.getPropertyValue("Width"));
        int pageHeight = AnyConverter.toInt(xPageProps.getPropertyValue("Height"));
        int pageBorderTop = AnyConverter.toInt(xPageProps.getPropertyValue("BorderTop"));
        int pageBorderLeft = AnyConverter.toInt(xPageProps.getPropertyValue("BorderLeft"));
        int pageBorderRight = AnyConverter.toInt(xPageProps.getPropertyValue("BorderRight"));
        int drawWidth = pageWidth - pageBorderLeft - pageBorderRight;
        int horCenter = pageBorderLeft + drawWidth / 2;
        
        // data for organigram
        String[][] orgUnits = new String[2][4];
        orgUnits[0][0] = "Management"; // level 0
        orgUnits[1][0] = "Production"; // level 1
        orgUnits[1][1] = "Purchasing"; // level 1
        orgUnits[1][2] = "IT Services"; // level 1
        orgUnits[1][3] = "Sales"; // level 1
        int[] levelCount = {1, 4};
        
        // calculate shape sizes and positions
        int horSpace = 300;
        int verSpace = 3000;
        int shapeWidth = (drawWidth - (levelCount[1] - 1) * horSpace) / levelCount[1];
        int shapeHeight = pageHeight / 20;
        int shapeX = pageWidth / 2 - shapeWidth / 2;
        int levelY = 0;
        com.sun.star.drawing.XShape xStartShape = null;
        // get document factory
        com.sun.star.lang.XMultiServiceFactory xDocumentFactory = (com.sun.star.lang.XMultiServiceFactory)
            UnoRuntime.queryInterface(
                com.sun.star.lang.XMultiServiceFactory.class, xDrawComponent);
//        com.sun.star.beans.XPropertySet  xShapePropSet = ShapeHelper.createAndInsertShape( xDrawComponent,
//        	      xDrawPage, new Point(1000, 1000), new Size(5000, 5000),
//        	      "com.sun.star.drawing.RectangleShape" );
//        	  // and now set an object animation
//        	  xShapePropSet.setPropertyValue("Effect", 
//        	      com.sun.star.presentation.AnimationEffect.WAVYLINE_FROM_BOTTOM);
        // creating and adding RectangleShapes and Connectors 
        for (int level = 0; level <= 1; level++) {
            levelY = pageBorderTop + 2000 + level * (shapeHeight + verSpace);
            for (int i = levelCount[level] - 1; i > -1; i--) {
                shapeX = horCenter - 
                         (levelCount[level] * shapeWidth + (levelCount[level] - 1) * horSpace) / 2 + 
                         i * shapeWidth + i * horSpace;
                Object shape = xDocumentFactory.createInstance("com.sun.star.drawing.RectangleShape");
                com.sun.star.drawing.XShape xShape = (com.sun.star.drawing.XShape)
                    UnoRuntime.queryInterface(
                        com.sun.star.drawing.XShape.class, shape);
                xShape.setPosition(new com.sun.star.awt.Point(shapeX, levelY));
                xShape.setSize(new com.sun.star.awt.Size(shapeWidth, shapeHeight)); 
                
                xDrawPage.add(xShape);
                com.sun.star.beans.XPropertySet xShapeProps = (com.sun.star.beans.XPropertySet)
                        UnoRuntime.queryInterface(
                            com.sun.star.beans.XPropertySet.class, xShape);
                xShapeProps.setPropertyValue("Effect", com.sun.star.presentation.AnimationEffect.APPEAR);
                
                
                // set the text
                com.sun.star.text.XText xText = (com.sun.star.text.XText)
                    UnoRuntime.queryInterface(
                        com.sun.star.text.XText.class, xShape);
                xText.setString(orgUnits[level][i]); 
                // memorize the root shape, for connectors
                if (level == 0 && i == 0)
                    xStartShape = xShape; 
                
                // add connectors for level 1
                if (level == 1) {
                    Object connector = xDocumentFactory.createInstance(
                        "com.sun.star.drawing.ConnectorShape");
                    com.sun.star.drawing.XShape xConnector = (com.sun.star.drawing.XShape)
                        UnoRuntime.queryInterface(
                    com.sun.star.drawing.XShape.class, connector); 
                    xDrawPage.add(xConnector);
                    com.sun.star.beans.XPropertySet xConnectorProps = (com.sun.star.beans.XPropertySet)
                        UnoRuntime.queryInterface(
                            com.sun.star.beans.XPropertySet.class, connector);
                    xConnectorProps.setPropertyValue("StartShape", xStartShape);
                    xConnectorProps.setPropertyValue("EndShape", xShape);
                    // glue point positions: 0=top 1=left 2=bottom 3=right
                    xConnectorProps.setPropertyValue("StartGluePointIndex", new Integer(2)); 
                    xConnectorProps.setPropertyValue("EndGluePointIndex", new Integer(0)); 
                }
      
            }
        }
        
        
        
    }
    public static void storeComponent(XComponent document) {
    	String storeUrl = "file://drawing.odt";

    	// Save the document
    	XStorable xStorable = ( XStorable )UnoRuntime.queryInterface(XStorable.class, document);
    	PropertyValue[] storeProps = new PropertyValue[0];
    	try {
			xStorable.storeAsURL(storeUrl, storeProps);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    };
//    public static XPropertySet createAndInsertShape( XComponent xDrawDoc,
//            XShapes xShapes, Point aPos, Size aSize, String sShapeType) throws java.lang.Exception {
//        XShape xShape = createShape(xDrawDoc, aPos, aSize, sShapeType);
//        xShapes.add(xShape);
//        XPropertySet xPropSet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, xShape);
//        return xPropSet;
//    }

}
