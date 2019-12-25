// 
// Decompiled by Procyon v0.5.36
// 

package Model.command;

import Environment.Environment;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Model.DigitalPattern.DigitalPattern;
import Model.CommandMessages;
import java.util.List;

public class SetPatternCommand extends Command
{
    private final int PARAM_COUNT = 2;
    private final int PATTERN_POS = 0;
    private final int NAME_POS = 1;
    private String SUCCESS_CREATED;
    private String INVALID_INPUT;
    private String ALREADY_DEFINED;
    private String NO_PATTERN;
    private String MAX_LEN_NAME;
    private String INVALID_PATTERN_NAME;
    private final String PATTERN_NAME_REGEX = "^[a-zA-Z]{1,}[a-zA-Z0-9]{2,}$";
    private final String INVALID_PATTERN;
    
    public SetPatternCommand(final List<String> args) {
        this.SUCCESS_CREATED = CommandMessages.getMessage("SUCCESS_CREATED_PATTERN");
        this.INVALID_INPUT = CommandMessages.getMessage("INVALID_INPUT");
        this.ALREADY_DEFINED = CommandMessages.getMessage("ALREADY_DEFINED_PATTERN");
        this.NO_PATTERN = CommandMessages.getMessage("NO_PATTERN");
        this.MAX_LEN_NAME = CommandMessages.getMessage("EXPECT_20_CHAR_PATTERN_NAME_MAX");
        this.INVALID_PATTERN_NAME = CommandMessages.getMessage("INVALID_PATTERN_NAME");
        this.INVALID_PATTERN = CommandMessages.getMessage("INVALID_PATTERN");
        if (args != null) {
            if (args.size() == 2) {
                final String rawName = args.get(1).replace("\"", "");
                final String rawPattern = args.get(0).replace("\"", "");
                final boolean patternCorrect = this.processPattern(rawPattern);
                final boolean nameCorrect = this.processName(rawName);
                if (!this.processPattern(rawPattern)) {
                    this.returnValue = this.INVALID_PATTERN;
                }
                else if (patternCorrect && nameCorrect) {
                    final DigitalPattern newPattern = new DigitalPattern(rawPattern, rawName);
                    DigitalPattern.addPattern(newPattern);
                    this.returnValue = this.SUCCESS_CREATED;
                }
                else {
                    this.returnValue = this.INVALID_PATTERN_NAME;
                }
            }
            else {
                this.returnValue = this.INVALID_INPUT;
            }
        }
        else {
            this.returnValue = this.NO_PATTERN;
        }
    }
    
    private boolean processName(final String name) {
        final String rawName = name.replace("\"", "");
        Boolean nameValid = true;
        final Matcher matcher = Pattern.compile("^[a-zA-Z]{1,}[a-zA-Z0-9]{2,}$").matcher(rawName);
        if (!matcher.matches()) {
            nameValid = false;
        }
        return nameValid;
    }
    
    private boolean processPattern(final String pattern) {
        final String[] items = pattern.split(" ");
        final List<String> itemList = new ArrayList<String>();
        Boolean validPattern = true;
        for (final String item : items) {
            final int singleItem = Integer.parseInt(item);
            if (singleItem == 0) {
                validPattern = false;
            }
            else if (singleItem > 8) {
                validPattern = false;
            }
            else if (singleItem < 1) {
                validPattern = false;
            }
            else {
                itemList.add(item);
            }
        }
        return validPattern;
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
}
