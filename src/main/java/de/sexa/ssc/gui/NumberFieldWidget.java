package de.sexa.ssc.gui;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class NumberFieldWidget extends TextFieldWidget {

    public NumberFieldWidget(TextRenderer textRenderer, int x, int y, int width, int height, @Nullable TextFieldWidget copyFrom, Text text) {
        super(textRenderer, x, y, width, height, copyFrom, text);
    }

    @Override
    public void write(String text) {
        if (!isDigitString(text)) return;

        String newText = this.getText() + text;


        if (!newText.isEmpty() && !isValidString(newText)) return;


        super.write(text);
    }




    private boolean isValidString(String str) {
        if (!isDigitString(str)) return false;


        int number;
        try {
            number = Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }

        return number <= 255;
    }
    private boolean isDigitString(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }
}
