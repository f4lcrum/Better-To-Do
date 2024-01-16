package cz.fi.muni.pv168.todo.ui.dialog;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import cz.fi.muni.pv168.todo.ui.custom.PlaceholderLabel;
import cz.fi.muni.pv168.todo.ui.custom.PlaceholderTextArea;
import cz.fi.muni.pv168.todo.ui.custom.PlaceholderTextField;
import cz.fi.muni.pv168.todo.business.service.validation.ValidationResult;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import java.util.List;
import java.util.Objects;
import net.miginfocom.swing.MigLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.util.Optional;

abstract class EntityDialog<E> {

    protected final JPanel panel = new JPanel();
    private final JPanel errors = new JPanel();
    private final Validator<E> entityValidator;


    EntityDialog(Validator<E> entityValidator) {
        this.entityValidator = Objects.requireNonNull(entityValidator);
        panel.setLayout(new MigLayout("wrap 2"));
        errors.setLayout(new MigLayout("wrap 1"));
    }

    protected void addErrorPanel() {
        panel.add(errors, "span");
    }

    void add(String labelText, String placeholder, PlaceholderTextField component) {
        component.setPlaceholder(new PlaceholderLabel(placeholder));
        addMandatory(labelText, component);
    }

    void addMandatory(String labelText, JComponent component) {
        var label = new JLabel(String.format("<html>%s<font color='red'>%s</font>: </html>", labelText, "*"));
        add(label, component);
    }

    void addOptional(String labelText, JComponent component) {
        var label = new JLabel(String.format("%s: ", labelText));
        add(label, component);
    }

    private void add(JLabel label, JComponent component) {
        panel.add(label);
        panel.add(component, "wmin 250lp, grow");
    }

    void addDescription(String labelText, String placeholder, PlaceholderTextArea component) {
        var label = new JLabel(String.format("%s: ", labelText));
        component.setLineWrap(true);
        component.setPlaceholder(new PlaceholderLabel(placeholder));
        JScrollPane pane = new JScrollPane(component);
        panel.add(label);
        panel.add(pane, "wmin 250lp, hmin 100lp, grow");
    }

    void addTime(String labelText, PlaceholderTextField hours, PlaceholderTextField minutes) {
        var label = new JLabel(String.format("<html>%s<font color='red'>%s</font>: </html>", labelText, "*"));
        hours.setPlaceholder(new PlaceholderLabel("8"));
        minutes.setPlaceholder(new PlaceholderLabel("30"));
        panel.add(label);
        panel.add(hours, "wmin 30, split 4");
        panel.add(new JLabel("h"));
        panel.add(minutes, "wmin 30");
        panel.add(new JLabel("m"));
    }

    abstract E getEntity();
    abstract ValidationResult isValid();

    private void showErrorMessages(List<String> messages) {
        errors.removeAll();
        messages.stream().map(JLabel::new).forEach(errors::add);
    }

    private int showOptionDialog(JComponent parentComponent, String title) {
        return JOptionPane.showOptionDialog(
                parentComponent, panel, title, OK_CANCEL_OPTION, PLAIN_MESSAGE,
                null, null, null
        );
    }

    public void showWithErrors(JComponent parentComponent, String title, ValidationResult validationResult) {
        showErrorMessages(validationResult.getValidationErrors());
        show(parentComponent, title);
    }

    public Optional<E> show(JComponent parentComponent, String title) {
        int result = JOptionPane.showOptionDialog(parentComponent, panel, title, OK_CANCEL_OPTION, PLAIN_MESSAGE, null, null, null);

        while (result == OK_OPTION) {
            var validateFields = isValid();
            if (validateFields.isValid()) {
                var entity = getEntity();
                var validation = entityValidator.validate(entity);

                if (validation.isValid()) {
                    return Optional.of(entity);
                }

                showErrorMessages(validation.getValidationErrors());
                result = showOptionDialog(parentComponent, title);
            } else {

                result = showOptionDialog(parentComponent, title);
            }
        }

        return Optional.empty();
    }
}
