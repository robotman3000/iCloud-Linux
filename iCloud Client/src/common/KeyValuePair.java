package common;

public class KeyValuePair{

        private String key;
        private String value;

        public KeyValuePair(String key1, String value){
                this.setKey(key1);
                this.setValue(value);
        }

        public String getValue() {
                return value;
        }

        public void setValue(String value) {
                this.value = value;
        }

        public String getKey() {
                return key;
        }

        public void setKey(String key) {
                this.key = key;
        }
}
