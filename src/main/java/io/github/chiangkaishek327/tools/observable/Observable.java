package io.github.chiangkaishek327.tools.observable;

public interface Observable<Type> {

    public Listener<Type> getChangeListener();

    public void setChangeListener(Listener<Type> listener);

    public static interface Listener<Type> {
        public static class Event {
            public static class ValueEvent extends Event {
                public static final ValueEvent EVENT_SET = new ValueEvent();
            }

            public static class ArrayEvent extends ValueEvent {
                public static final ArrayEvent EVENT_ADD = new ArrayEvent(), EVENT_REMOVE = new ArrayEvent();
            }
        }

        /**
         * 
         * @param event    Operation for observable
         * @param oldValue value before changed
         * @param newValue value after changed
         */
        public void changed(Event event, Type oldValue, Type newValue);

    }
}
