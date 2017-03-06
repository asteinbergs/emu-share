package test.java;

import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;

public class ScreenStreamTestServer extends VlcjTest {

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("Specify a single MRL to stream");
			System.exit(1);
		}
		new NativeDiscovery().discover();
		String media = args[0];
		// String publicIP = "192.168.0.255";
		String publicIP = "230.0.0.1";
		short publicPort = 5555;
		String options = formatRtpStream(publicIP, publicPort);

		System.out.println("Streaming '" + media + "' to '" + options + "'");

		MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(args);
		HeadlessMediaPlayer mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();

		mediaPlayer.playMedia(media, options, ":no-sout-rtp-sap", ":no-sout-standard-sap", ":sout-all", ":sout-keep");

		Thread.currentThread().join(); // Don't exit
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