import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/*
class used for writing/reading scores from the file
 */
public class WritetToFile {

    /*
    Appends the score to the file on a new line in the file
    */
    public void write(int score) {
        try (FileWriter fw = new FileWriter("scores.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(score);

        } catch (IOException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }

    /*
    reads from the text file & outputs the top 5 scores
    */
    public void top5(Graphics g) {
        int x = 350, y = 400, j = 1;
        ArrayList<Integer> scoresList = new ArrayList<>();
        g.drawString("Top 5 scores:", x, y);
        y += 20;
        try {
            File myObj = new File("scores.txt");

            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                scoresList.add(Integer.valueOf(data));
            }
            scoresList.sort(Collections.reverseOrder());
            if (scoresList.size() > 5) {
                for (int i = 0; i < 5; i++) {
                    g.drawString(i + 1 + ". " + scoresList.get(i).toString(), x, y);
                    y += 20;
                }
            } else {
                for (Integer s : scoresList) {
                    // System.out.println(s);
                    g.drawString(j + ". " + s.toString(), x, y);
                    y += 20;
                    j++;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Created the 'scores.txt' file and stored the score");


        }
    }
}
