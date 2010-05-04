package co.edu.eafit.analisisnumerico.Video;

import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JFileChooser;
import javax.swing.JFrame;


/**
 *
 * @author nico
 */
public class MediaTest
{
	
   // launch the application
   public MediaTest( String metodo )
   {
         URL mediaURL = null;

         try
         {
             //mediaURL=new URL("file:/C:/Documents and Settings/nico/Desktop/ReglaFalsa.mpg");
             mediaURL=new URL("file:/ayudasInterfaz/ReglaFalsa.mpg");
             
        	 
            System.out.println(mediaURL);
         } // end try
         catch ( MalformedURLException malformedURLException )
         {
            System.err.println( "Could not create URL for the file");
         } // end catch

         if ( mediaURL != null ) // only display if there is a valid URL
         {
            JFrame mediaTest = new JFrame( "Media Tester" );
            mediaTest.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

            MediaPanel mediaPanel = new MediaPanel( mediaURL );
            mediaTest.add( mediaPanel );

            mediaTest.setSize( 700, 700 );
            mediaTest.setVisible( true );
         }
   }
}


