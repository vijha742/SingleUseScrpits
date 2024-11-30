import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SubtitleToolgpt {
    public static void main(String[] args) {
        List<Subtitle> subtitles = new ArrayList<>();
        
        // Reading the input file
        try (Scanner read = new Scanner(new File("/home/vikas/Desktop/Harvard CS50 – Full Computer Science University Course - YouTube - English.srt"))) {
            while (read.hasNextLine()) {
                // Reading subtitle number
                String number = read.nextLine();
                
                // Reading time frame
                String timeFrame = read.nextLine();
                
                // Reading subtitle text
                StringBuilder text = new StringBuilder();
                String line;
                while (read.hasNextLine() && !(line = read.nextLine()).isEmpty()) {
                    text.append(line).append("\n");
                }
                
                // Storing the subtitle
                subtitles.add(new Subtitle(number, timeFrame, text.toString().trim()));
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return;
        }

        // Adjusting the end times of subtitles
        for (int i = 0; i < subtitles.size() - 1; i++) {
            Subtitle current = subtitles.get(i);
            Subtitle next = subtitles.get(i + 1);
            String newEndTime = next.getStartTime();
            current.setEndTime(newEndTime);
        }
        
        // Writing to the output file
        try (FileWriter subWriter = new FileWriter("/home/vikas/Desktop/trialfile.srt")) {
            for (Subtitle subtitle : subtitles) {
                subWriter.write(subtitle.getNumber() + "\n");
                subWriter.write(subtitle.getStartTime() + " --> " + subtitle.getEndTime() + "\n");
                subWriter.write(subtitle.getText() + "\n\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing the file: " + e.getMessage());
        }

        System.out.println("All is well...");
    }
}

class Subtitle {
    private String number;
    private String startTime;
    private String endTime;
    private String text;

    public Subtitle(String number, String timeFrame, String text) {
        this.number = number;
        String[] times = timeFrame.split(" --> ");
        this.startTime = times[0];
        this.endTime = times[1];
        this.text = text;
    }

    public String getNumber() {
        return number;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getText() {
        return text;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
