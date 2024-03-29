package cz.fi.muni.pv168.todo.ui.model;

import cz.fi.muni.pv168.todo.util.Either;

import javax.swing.ComboBoxModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * This class decorates existing {@link javax.swing.ListModel} or {@link ComboBoxModel} to contain also
 * custom {@link Enum} values at the start of the model values.
 *
 * @author Vojtěch Sassmann
 */
public final class CustomValuesModelDecorator {

    private CustomValuesModelDecorator() {
    }

    public static <T extends Enum<T>, E> ListModel<Either<T, E>> addCustomValues(Class<T> clazz,
                                                                                 ListModel<E> decoratedModel) {
        return new ListModelDecorator<>(clazz, decoratedModel);
    }

    private static class ListModelDecorator<T extends Enum<T>, E, M extends ListModel<E>>
            implements ListModel<Either<T, E>> {

        private final Class<T> clazz;
        private final int enumerationSize;
        private final M decoratedModel;
        private final Map<ListDataListener, TransposingListener> listeners = new IdentityHashMap<>();

        private ListModelDecorator(Class<T> clazz, M decoratedModel) {
            this.clazz = clazz;
            this.enumerationSize = clazz.getEnumConstants().length;
            this.decoratedModel = decoratedModel;
        }

        @Override
        public int getSize() {
            return decoratedModel.getSize() + enumerationSize;
        }

        @Override
        public Either<T, E> getElementAt(int index) {
            if (0 <= index && index < enumerationSize) {
                return Either.left(clazz.getEnumConstants()[index]);
            } else {
                return Either.right(decoratedModel.getElementAt(index - enumerationSize));
            }
        }

        @Override
        public void addListDataListener(ListDataListener listener) {
            var transposingListener = new TransposingListener(listener, enumerationSize);
            listeners.put(listener, transposingListener);
            decoratedModel.addListDataListener(transposingListener);
        }

        @Override
        public void removeListDataListener(ListDataListener listener) {
            TransposingListener transposingListener = listeners.remove(listener);
            if (transposingListener != null) {
                decoratedModel.removeListDataListener(transposingListener);
            }
        }

    }

    private static class TransposingListener implements ListDataListener {

        private final ListDataListener delegate;
        private final int transposition;

        private TransposingListener(ListDataListener delegate, int transposition) {
            this.delegate = delegate;
            this.transposition = transposition;
        }

        @Override
        public void intervalAdded(ListDataEvent event) {
            delegate.intervalAdded(transposeIndexes(event));
        }

        @Override
        public void intervalRemoved(ListDataEvent event) {
            delegate.intervalRemoved(transposeIndexes(event));
        }

        @Override
        public void contentsChanged(ListDataEvent event) {
            delegate.contentsChanged(transposeIndexes(event));
        }

        private ListDataEvent transposeIndexes(ListDataEvent event) {
            return new ListDataEvent(
                    event.getSource(),
                    event.getType(),
                    transposeIndex(event.getIndex0()),
                    transposeIndex(event.getIndex1())
            );
        }

        private int transposeIndex(int index) {
            return index >= 0 ? index + transposition : index;
        }
    }
}

