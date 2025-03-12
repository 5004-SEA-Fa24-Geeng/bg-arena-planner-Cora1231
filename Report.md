# Report

Submitted report to be manually graded. We encourage you to review the report as you read through the provided
code as it is meant to help you understand some of the concepts. 

## Technical Questions

1. What is the difference between == and .equals in java? Provide a code example of each, where they would return different results for an object. Include the code snippet using the hash marks (```) to create a code block.
   * "==" means reference equal; .equals means value is equal.
   ```java
   // public class EqualsVsDoubleEquals {
    public static void main(String[] args) {
        // Create two distinct String objects with the same content
        String str1 = new String("Hello");
        String str2 = new String("Hello");

        // '==' compares the references, so it returns false because they are different objects.
        System.out.println("Using == : " + (str1 == str2)); // Output: false

        // '.equals()' compares the values, so it returns true because both have the same content.
        System.out.println("Using .equals() : " + str1.equals(str2)); // Output: true
    }



```

   ```




2. Logical sorting can be difficult when talking about case. For example, should "apple" come before "Banana" or after? How would you sort a list of strings in a case-insensitive manner?
   * Using compareToIgnoreCase(): The lambda (s1, s2) -> s1.compareToIgnoreCase(s2)




3. In our version of the solution, we had the following code (snippet)
    ```java
    public static Operations getOperatorFromStr(String str) {
        if (str.contains(">=")) {
            return Operations.GREATER_THAN_EQUALS;
        } else if (str.contains("<=")) {
            return Operations.LESS_THAN_EQUALS;
        } else if (str.contains(">")) {
            return Operations.GREATER_THAN;
        } else if (str.contains("<")) {
            return Operations.LESS_THAN;
        } else if (str.contains("=="))...
    ```
    Why would the order in which we checked matter (if it does matter)? Provide examples either way proving your point. 
      * Order does matter. Imagine that longer operators (such as “>=” and “<=”) contain shorter operators (“>” and “< “) as substrings. If we check “>” before “>=”, then strings like “5>=3” will first match the “> “ condition, thus returning the wrong operator.


4. What is the difference between a List and a Set in Java? When would you use one over the other? 
* Lists need to maintain insertion order and allow positional access, plus they can contain duplicate elements. Sets are not guaranteed any particular order and each element is unique.




5. In [GamesLoader.java](src/main/java/student/GamesLoader.java), we use a Map to help figure out the columns. What is a map? Why would we use a Map here? 
* A Map is a collection that stores data in key-value pairs. The Map is used here to efficiently and reliably match the CSV columns with the corresponding fields in the BoardGame object, making the data loading process robust and flexible



6. [GameData.java](src/main/java/student/GameData.java) is actually an `enum` with special properties we added to help with column name mappings. What is an `enum` in Java? Why would we use it for this application?
* An enum in Java is a special type that represents a fixed set of constants，and This is to ensure that type-safe, fixed sets of column identifiers are used and that the logic for handling CSV column names is encapsulated in one place.






7. Rewrite the following as an if else statement inside the empty code block.
    ```java
    switch (ct) {
                case CMD_QUESTION: // same as help
                case CMD_HELP:
                    processHelp();
                    break;
                case INVALID:
                default:
                    CONSOLE.printf("%s%n", ConsoleText.INVALID);
            }
    ``` 

    ```java
   // 
   if (ct == ConsoleText.CMD_QUESTION || ct == ConsoleText.CMD_HELP) {
    processHelp();
   } else {
   CONSOLE.printf("%s%n", ConsoleText.INVALID);
}


    ```

## Deeper Thinking

ConsoleApp.java uses a .properties file that contains all the strings
that are displayed to the client. This is a common pattern in software development
as it can help localize the application for different languages. You can see this
talked about here on [Java Localization – Formatting Messages](https://www.baeldung.com/java-localization-messages-formatting).

Take time to look through the console.properties file, and change some of the messages to
another language (probably the welcome message is easier). It could even be a made up language and for this - and only this - alright to use a translator. See how the main program changes, but there are still limitations in 
the current layout. 

Post a copy of the run with the updated languages below this. Use three back ticks (```) to create a code block. 

```text
//     <entry key="welcome">
*******Helkome BoardGame Arena Plannar.*******

Granish: Lapush the helk of board games. 
Briajs to begin your plonks, or type ? or help for grobk options.
```

Now, thinking about localization - we have the question of why does it matter? The obvious
one is more about market share, but there may be other reasons.  I encourage
you to take time researching localization and the importance of having programs
flexible enough to be localized to different languages and cultures. Maybe pull up data on the
various spoken languages around the world? What about areas with internet access - do they match? Just some ideas to get you started. Another question you are welcome to talk about - what are the dangers of trying to localize your program and doing it wrong? Can you find any examples of that? Business marketing classes love to point out an example of a car name in Mexico that meant something very different in Spanish than it did in English - however [Snopes has shown that is a false tale](https://www.snopes.com/fact-check/chevrolet-nova-name-spanish/).  As a developer, what are some things you can do to reduce 'hick ups' when expanding your program to other languages?
* The success of Black Myth: Wukong, a highly anticipated Chinese 3A game masterpiece, lies not only in its excellent production and deep cultural heritage, but also in its focus on the global market, especially its dedication to localization. Data from the British Council suggests that there are approximately 1.5 billion non-native English speakers globally[1].  Through this game, we can delve into the importance of localization and why video game localization is so crucial.Localization can expand market coverage and enhance global reach. Set against the backdrop of the Chinese classic Journey to the West, Black Myth: Wukong incorporates a large number of traditional Chinese cultural elements. However, it may be unfamiliar to non-Chinese players due to cultural differences. Through localization, the game can present these cultural elements to global players in a more understandable way, thus attracting more users. For example, translating texts, adjusting voiceovers, and modifying UI design to adapt to the reading habits of different languages are all key steps in localization.In addition, localization can enhance user experience and improve player immersion[2]. Localization is not only about language translation, but also about adapting to culture, customs and aesthetics[3].To make localization effective, 1.Need to work with localization experts and hire a translation team that is familiar with the target language and culture to ensure the accuracy and cultural appropriateness of the translation.2.Conduct localization tests. Before the game is released, invite players in the target region to conduct tests, collect feedback and fix problems.3. Focus on details. From text translation to voice-over tone, from UI design to cultural symbols, every detail may affect the player's experience.


As a reminder, deeper thinking questions are meant to require some research and to be answered in a paragraph for with references. The goal is to open up some of the discussion topics in CS, so you are better informed going into industry.

* [1] American TESOL Institute. (2025, January 1). Report on the global distribution of native and non-native English speakers. Report on the Global Distribution of Native and Non-Native English Speakers. https://americantesol.com/tesol-report.html
* [2] Naphade, A. (2024, November 4). The impact of localization QA on game performance and player engagement. GlobalStep. https://globalstep.com/the-impact-of-localization-qa-on-game-performance-and-player-engagement/ 
* [3] How to build a successful localization strategy: Top ten tips and examples. Weglot. (n.d.). https://www.weglot.com/guides/localization-strategy 