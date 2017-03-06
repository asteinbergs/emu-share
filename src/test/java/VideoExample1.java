package test.java;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;

public class VideoExample1 {
	
	private static final String[] OPTIONS = {
	        "--quiet",
	        "--quiet-synchro",
	        "--intf",
	        "dummy"
	    };
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setLocation(100, 100);
		f.setSize(1000, 600);
		// f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// f.setUndecorated(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

		Canvas c = new Canvas();
		c.setBackground(Color.black);
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());

		p.add(c);
		f.add(p);

		new NativeDiscovery().discover();

		MediaPlayerFactory mpf = new MediaPlayerFactory(OPTIONS);

		EmbeddedMediaPlayer emp = mpf.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(f));
		emp.setVideoSurface(mpf.newVideoSurface(c));
		emp.setEnableMouseInputHandling(false);
		emp.setEnableKeyInputHandling(false);

		String file = "The Meme Jihad.mp4";
		emp.prepareMedia(file);
		emp.play();
	}

}
