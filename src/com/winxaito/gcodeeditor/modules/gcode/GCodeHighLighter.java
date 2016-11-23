package com.winxaito.gcodeeditor.modules.gcode;

import com.winxaito.gcodeeditor.Main;
import javafx.concurrent.Task;
import javafx.scene.control.Tab;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by: WinXaito (Kevin Vuilleumier)
 */
public class GCodeHighLighter{
    private static CodeArea codeArea;
    private ExecutorService executor;

    private static final String[] KEYWORDS_G = new String[] {
            "G00", "G01", "G02", "G03", "G04",
            "G10", "G12", "G13", "G15", "G16",
            "G17", "G18", "G19", "G20", "G21",
            "G28", "G29", "G30", "G31", "G40",
            "G41", "G42", "G43", "G49", "G50",
            "G51", "G52", "G53", "G54", "G55",
            "G56", "G57", "G58", "G59", "G61",
            "G64", "G68", "G69", "G70", "G71",
            "G73", "G80", "G81", "G82", "G83",
            "G84", "G85", "G86", "G87", "G88",
            "G89", "G90", "G91", "G92", "G93",
            "G94", "G95", "G98", "G99",
            "G0", "G1", "G2", "G3", "G4"
    };

    private static final String[] KEYWORDS_M = new String[] {
            "M00", "M01", "M02", "M03", "M04",
            "M05", "M06", "M07", "M08", "M09",
            "M30", "M47", "M48", "M49", "M82",
            "M84", "M98", "M99", "M104", "M106",
            "M107", "M109", "M140",
            "M0", "M1", "M2", "M3", "M4",
            "M5", "M6", "M7", "M8", "M9",
    };

    private static final String[] KEYWORDS_ARGS_POS = new String[] {
            "X", "Y", "Z", "A", "B", "C", "U", "V", "W", "E", "P",
    };

    private static final String[] KEYWORDS_ARGS_SPEED = new String[] {
            "S", "F",
    };

    private static final String GCODE_PATTERN       = "(" + String.join("|", (CharSequence[])KEYWORDS_G) + ")( |\n|$)";
    private static final String MCODE_PATTERN       = "(" + String.join("|", (CharSequence[])KEYWORDS_M) + ")( |\n|$)";
    private static final String ARGS_POS_PATTERN    = "(" + String.join("|", (CharSequence[])KEYWORDS_ARGS_POS) + ")([+-]?[0-9.]+)?( |\n|$)";
    private static final String ARGS_SPEED_PATTERN  = "(" + String.join("|", (CharSequence[])KEYWORDS_ARGS_SPEED) + ")([+-]?[0-9.]+)?( |\n|$)";
    private static final String NUMBER_LINE_PATTERN = "(^|\n)N[0-9]+( |\n|$)";
    private static final String COMMENT_PATTERN     = ";[^\\n]*";


    public GCodeHighLighter(CodeArea codeArea, Tab tab){
        executor = Executors.newSingleThreadExecutor();
        GCodeHighLighter.codeArea = codeArea;
        codeArea.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved()))
                .successionEnds(Duration.ofMillis(100))
                .supplyTask(this::computeHighlightingAsync)
                .awaitLatest(codeArea.richChanges())
                .filterMap(t -> {
                    if(t.isSuccess()) {
                        return Optional.of(t.get());
                    } else {
                        t.getFailure().printStackTrace();
                        return Optional.empty();
                    }
                })
                .subscribe(this::applyHighlighting);



        Main.primaryStage.setOnCloseRequest(event -> executor.shutdown());
        tab.setOnCloseRequest(event -> executor.shutdown());
    }

    private static final Pattern PATTERN = Pattern.compile(
            "(?<GCODE>" + GCODE_PATTERN + ")"
                    + "|(?<MCODE>" + MCODE_PATTERN + ")"
                    + "|(?<ARGSPOS>" + ARGS_POS_PATTERN + ")"
                    + "|(?<ARGSSPEED>" + ARGS_SPEED_PATTERN + ")"
                    + "|(?<NUMBERLINE>" + NUMBER_LINE_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    private Task<StyleSpans<Collection<String>>> computeHighlightingAsync() {
        String text = codeArea.getText();
        Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
            @Override
            protected StyleSpans<Collection<String>> call() throws Exception {
                return computeHighlighting(text);
            }
        };
        executor.execute(task);
        return task;
    }

    private void applyHighlighting(StyleSpans<Collection<String>> highlighting) {
        codeArea.setStyleSpans(0, highlighting);
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();

        while(matcher.find()) {
            String styleClass =
                    matcher.group("GCODE") != null ? "gcode" :
                            matcher.group("MCODE") != null ? "mcode" :
                                    matcher.group("ARGSPOS") != null ? "args_pos" :
                                            matcher.group("ARGSSPEED") != null ? "args_speed" :
                                                    matcher.group("NUMBERLINE") != null ? "number_line" :
                                                            matcher.group("COMMENT") != null ? "comment" :
                                                                    null; /* never happens */ assert styleClass != null;

            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();

            if(styleClass.equalsIgnoreCase("number_line"))
                System.out.println("NUMBER_LINE");
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
}
