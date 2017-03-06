package test.java;

import java.awt.Canvas;
import java.awt.Color;

import javax.swing.JFrame;

import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;

public class ScreenStreamTestClient extends VlcjTest {

	public static void main(String[] args) throws Exception {
		new NativeDiscovery().discover();
		String mediatorIP = "192.168.1.104"; short mediatorPort = 6001;
		String publicIP, publicServer, localIP, localServer, clientIP;
		short publicPort, localPort;

		MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(args);
		EmbeddedMediaPlayer mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();

		Canvas canvas = new Canvas();
		canvas.setBackground(Color.black);
		CanvasVideoSurface videoSurface = mediaPlayerFactory.newVideoSurface(canvas);
		mediaPlayer.setVideoSurface(videoSurface);

		JFrame f = new JFrame();
		//f.setIconImage(new ImageIcon(Client.class.getResource("icons/vlcj-logo.png")).getImage());
		f.add(canvas);
		f.setSize(800, 600);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		publicIP = "@230.0.0.1"; // Recv public server's ip from mediator
		publicPort = 5555; // Recv public server's port from mediator
		publicServer = formatRtpStream(publicIP, publicPort);
		System.out.println("Capturing from '" + publicServer + "'");
		f.setTitle("Capturing from Public Server 'rtp://" + publicIP + ":" + publicPort + "'");
		mediaPlayer.playMedia(publicServer);
	}

	private static String formatRtpStream(String serverAddress, short serverPort) {

		StringBuilder sb = new StringBuilder(200);
		// sb.append(":sout=#transcode{acodec=mp4a,samplerate=12000,width=400,height=300}:rtp{dst=");
		// sb.append("::sout=#transcode{vcodec=mp4v,vb=4096,scale=1,fps=30,acodec=mpga,ab=128,channels=2,samplerate=44100,width=800,height=600}:rtp:duplicate{dst=file{dst=");
		sb.append(
				"::sout=#transcode{vcodec=mp4v,vb=3000,fps=30,scale=1,acodec=mp4a,ab=128,channels=2,samplerate=48000,width=800,height=600}:rtp{dst=");

		sb.append(serverAddress);
		sb.append(",port=");
		sb.append(serverPort);
		sb.append(",mux=ts}");
		return sb.toString();
	}
}