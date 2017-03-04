import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class Test {
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setLocation(100,100);
		f.setSize(1000,600);
		//f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//f.setUndecorated(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		Canvas c = new Canvas();
		c.setBackground(Color.black);
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		
		p.add(c);
		f.add(p);
		
		String vlcPath = "C:/Program Files/VideoLAN/VLC";
		System.out.println(vlcPath);
		System.out.println(RuntimeUtil.getLibVlcLibraryName());
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),vlcPath);
		System.out.println("1");
		RuntimeUtil.getLibVlcLibraryName();
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
		System.out.println("2");
		RuntimeUtil.getLibVlcLibraryName();
		
		MediaPlayerFactory mpf = new MediaPlayerFactory();
		System.out.println("3");
		
		EmbeddedMediaPlayer emp = mpf.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(f));
		emp.setVideoSurface(mpf.newVideoSurface(c));
		emp.setEnableMouseInputHandling(false);
		emp.setEnableKeyInputHandling(false);
		
		String file = "The Meme Jihad.mp4";
		emp.prepareMedia(file);
		emp.play();
	}
	
}
