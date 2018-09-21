package www.androidcitizen.com.jokeslib;

import java.util.Random;

public class JokeTeller {

    private int jokeSize;
    private Random random = new Random();
    private String[] jokes = jokeGenerator();

    public JokeTeller() {
        jokeSize = jokes.length;
    }

    private String[] jokeGenerator() {

        return new String[] {
                "Did you hear about the great new restaurant on the moon? \n" +
                        " A: The food is excellent, but there's no atmosphere." ,
                "Why wasn't the moon hungry? \n" +
                        " A: Because it was full!" ,
                "*Sleeping during conference call*\n" +
                        "\n" +
                        "Manager: Kunal are you on call?\n" +
                        "\n" +
                        "*Wakes up*\n" +
                        "\n" +
                        "Kunal: Sorry! I was talking on mute",
                "My wife told me she needs more space. I said no problem and locked her out of\n" +
                        "the house.\n",
                "The bartender asked the man “Whats wrong,why are you so down today?”.\n" +
                        "The man said “My wife and i got into a fight,and she said she would’nt talk to me for a month”.\n" +
                        "The bartender said “So whats wrong with that”?\n" +
                        "The man siad “Well the month is up tonight”."
        };
    }

    public String getJoke() {
        return jokes[random.nextInt(jokeSize)];
    }
}
