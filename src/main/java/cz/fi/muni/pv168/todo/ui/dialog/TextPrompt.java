package cz.fi.muni.pv168.todo.ui.dialog;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * @author https://github.com/tips4java
 * @link https://github.com/tips4java/tips4java
 * <p>
 * The TextPrompt class will display a prompt over top of a text component when
 * the Document of the text field is empty. The Show property is used to
 * determine the visibility of the prompt.
 * <p>
 * The Font and foreground Color of the prompt will default to those properties
 * of the parent text component. You are free to change the properties after
 * class construction.
 */
public class TextPrompt extends JLabel
        implements FocusListener, DocumentListener {

    private final JTextComponent component;
    private final Document document;
    private int focusLost;

    public TextPrompt(String text, JTextComponent component) {
        this.component = component;
        document = component.getDocument();

        setText(text);
        setFont(component.getFont());
        setForeground(component.getForeground());
        setBorder(new EmptyBorder(component.getInsets()));
        setHorizontalAlignment(JLabel.LEADING);

        component.addFocusListener(this);
        document.addDocumentListener(this);

        component.setLayout(new BorderLayout());
        component.add(this);
        checkForPrompt();
        changeStyle(Font.ITALIC);
        changeAlpha(0.5f);
    }

    /**
     * Convenience method to change the alpha value of the current foreground
     * Color to the specifice value.
     *
     * @param alpha value in the range of 0 - 1.0.
     */
    private void changeAlpha(float alpha) {
        changeAlpha((int) (alpha * 255));
    }

    /**
     * Convenience method to change the alpha value of the current foreground
     * Color to the specifice value.
     *
     * @param alpha value in the range of 0 - 255.
     */
    private void changeAlpha(int alpha) {
        alpha = alpha > 255 ? 255 : alpha < 0 ? 0 : alpha;

        Color foreground = getForeground();
        int red = foreground.getRed();
        int green = foreground.getGreen();
        int blue = foreground.getBlue();

        Color withAlpha = new Color(red, green, blue, alpha);
        super.setForeground(withAlpha);
    }

    /**
     * Convenience method to change the style of the current Font. The style
     * values are found in the Font class. Common values might be:
     * Font.BOLD, Font.ITALIC and Font.BOLD + Font.ITALIC.
     *
     * @param style value representing the the new style of the Font.
     */
    private void changeStyle(int style) {
        setFont(getFont().deriveFont(style));
    }

    /**
     * Check whether the prompt should be visible or not. The visibility
     * will change on updates to the Document and on focus changes.
     */
    private void checkForPrompt() {
        //  Text has been entered, remove the prompt

        if (document.getLength() > 0) {
            setVisible(false);
            return;
        }


        //  Check the Show property and component focus to determine if the
        //  prompt should be displayed.

        setVisible(!component.hasFocus());
    }

//  Implement FocusListener

    public void focusGained(FocusEvent e) {
        checkForPrompt();
    }

    public void focusLost(FocusEvent e) {
        focusLost++;
        checkForPrompt();
    }

//  Implement DocumentListener

    public void insertUpdate(DocumentEvent e) {
        checkForPrompt();
    }

    public void removeUpdate(DocumentEvent e) {
        checkForPrompt();
    }

    public void changedUpdate(DocumentEvent e) {
    }
}