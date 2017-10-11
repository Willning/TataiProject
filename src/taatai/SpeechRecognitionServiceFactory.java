package taatai;

import processBuilder.ServiceFactory;

/**
 * Object that abstracts HTK framework
 */
public class SpeechRecognitionServiceFactory extends ServiceFactory {
    public static final String PLAY = "play";
    public static final String RECORD = "record";
    public static final String PROCESS = "process";

    /**
     * Default Constructor, automatically adds all the useful bash commands.
     */
    public SpeechRecognitionServiceFactory() {
        super();
        addProcess(PLAY, "aplay foo.wav");
        addProcess(RECORD, "arecord -d 3 -r 22050 -c 1 -i -t wav -f s16_LE foo.wav");
        addProcess(PROCESS, "HVite -H MaoriNumbers/HMMs/hmm15/macros" +
                " -H MaoriNumbers/HMMs/hmm15/hmmdefs -C MaoriNumbers/user/configLR " +
                " -w MaoriNumbers/user/wordNetworkNum -o SWT -l '*' -i recout.mlf -p 0.0 -s 5.0" +
                "  MaoriNumbers/user/dictionaryD MaoriNumbers/user/tiedList foo.wav");
    }
}
