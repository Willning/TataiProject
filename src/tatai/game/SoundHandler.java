package tatai.game;

import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import processBuilder.ProcessOutput;
import tatai.SpeechRecognitionServiceFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This class will handle all the sound processing that is requires by the game
 * Created by Winston on 10/26/2017.
 */
public class SoundHandler {

    public static final String SOUND_FILE = "foo.wav";
    public static final String RECOUT_FILE = "recout.mlf";
    public static final String SOUND_DIR = ".";
    private Game game;
    private SpeechRecognitionServiceFactory serviceFactory;

    public SoundHandler(Game game) {
        this.game = game;
        serviceFactory = new SpeechRecognitionServiceFactory();
    }

    /**
     * Turns the MLF file created by HTK to a string that usable by the game.
     *
     * @return
     */
    public ArrayList<String> getTextFromMLF() {
        ArrayList<String> wordsSpoken = new ArrayList<>();
        try {
            // Read the recout file
            File recoutFile = new File(SOUND_DIR + "/" + RECOUT_FILE);
            FileInputStream fileInputStream = new FileInputStream(recoutFile);
            byte[] data = new byte[(int) recoutFile.length()];
            fileInputStream.read(data);
            fileInputStream.close();
            String fullFile = new String(data, "UTF-8");
            String[] fileLines = fullFile.split("\n");

            // Remove the "sil" and the first two lines and the last line (see the for loop parameters).
            for (int i = 2; i < fileLines.length - 1; i++) {
                if (!fileLines[i].equals("sil")) {
                    wordsSpoken.add(fileLines[i]);
                }
            }
        } catch (IOException e) {
            wordsSpoken.add("Nothing heard");
        }
        return wordsSpoken;
    }

    /**
     * Process that is called by the Controller when the user presses the record button, on finishing it will call the Controller's recordingDone() function.
     */
    public void record() {
        Service<ProcessOutput> recordService = serviceFactory.makeService(SOUND_DIR, SpeechRecognitionServiceFactory.RECORD, new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                game.recordingDone();
            }
        });
        recordService.start();
    }

    /**
     * Process that called by the Controller, it will parse the sound file "foo.wav" and create a MLF file. On finishing it will called processingDone() on 'this'.
     */
    public void process() {
        Service<ProcessOutput> processService = serviceFactory.makeService(SOUND_DIR, SpeechRecognitionServiceFactory.PROCESS, new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                processingDone();
            }
        });
        processService.start();
    }

    /**
     * Process that is called and will try to play the created file.
     */

    public void play() {
        Service<ProcessOutput> playService = serviceFactory.makeService(SOUND_DIR, SpeechRecognitionServiceFactory.PLAY, new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) { game.playDone(); }
        });
        playService.start();
    }

    /**
     * Process that is called when processing (creation of MLF file) is finished. It checks the MLF file and decides on the next action based of whether
     * the user answer the question correctly and the current state of the game.
     */
    private void processingDone() {
        game.processAnswer();
    }

    public void deleteSound(){
        // Delete the created files.
        File soundDir = new File(SOUND_DIR);
        for (File file : soundDir.listFiles()) {
            if (file.getName().equals(RECOUT_FILE) || file.getName().equals(SOUND_FILE)) {
                file.delete();
            }
        }
    }
}
