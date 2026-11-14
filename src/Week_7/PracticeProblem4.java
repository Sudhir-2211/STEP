package Week7;

/**
 * Practice Problem 4: Universal Media Launcher
 *
 * TASK:
 * 1. Playable interface: play(), play(int fromSecond), pause().
 * 2. MediaFile abstract class: final fileId from static counter, abstract getFormatInfo().
 * 3. AudioFile extends MediaFile implements Playable.
 * 4. Podcast implements Playable directly.
 * 5. launchAll(Playable[] items) calls play() polymorphically.
 *
 * Design Justification:
 * AudioFile has an IS-A relationship with MediaFile because it is a physical media asset
 * with metadata, storage format, and file identity. In contrast, Podcast has a CAN-DO
 * capability relationship with Playable; it is a live streamed show that does not possess
 * or require file system properties like fileId or format descriptors.
 */
public class PracticeProblem4 {

    public interface Playable {
        String play();
        String play(int fromSecond);
        String pause();
    }

    public static abstract class MediaFile {
        private static int counter = 1000;
        private final String fileId;

        public MediaFile() {
            this.fileId = "MF-" + (++counter);
        }

        public abstract String getFormatInfo();

        public String getFileId() {
            return fileId;
        }
    }

    public static class AudioFile extends MediaFile implements Playable {
        private final String title;

        public AudioFile(String title) {
            super();
            this.title = title;
        }

        @Override
        public String play() {
            return "Playing audio: " + title;
        }

        @Override
        public String play(int fromSecond) {
            int mins = fromSecond / 60;
            int secs = fromSecond % 60;
            return "Playing audio: " + title + " from " + String.format("%d:%02d", mins, secs);
        }

        @Override
        public String pause() {
            return "Paused audio: " + title;
        }

        @Override
        public String getFormatInfo() {
            return "Audio file, ID: " + getFileId();
        }
    }

    public static class Podcast implements Playable {
        private final String showName;
        private final int episodeNumber;

        public Podcast(String showName, int episodeNumber) {
            this.showName = showName;
            this.episodeNumber = episodeNumber;
        }

        @Override
        public String play() {
            return "Streaming episode " + episodeNumber + " of " + showName;
        }

        @Override
        public String play(int fromSecond) {
            return "Streaming episode " + episodeNumber + " of " + showName + " from second " + fromSecond;
        }

        @Override
        public String pause() {
            return "Paused podcast: " + showName;
        }
    }

    public static void launchAll(Playable[] items) {
        if (items == null) return;
        for (Playable p : items) {
            if (p != null) {
                System.out.println(p.play());
            }
        }
    }

    public static void main(String[] args) {
        AudioFile a = new AudioFile("Morning Jazz");
        System.out.println(a.play());
        System.out.println(a.play(30));
        System.out.println(a.getFormatInfo());

        Podcast p = new Podcast("Tech Talk", 12);
        System.out.println(p.play());

        Playable ref = a; // upcasting
        launchAll(new Playable[]{ref, p});
    }
}
