package solution;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PhoneDirectoryEntry {
    private StringProperty lastName;
    private StringProperty firstName;
    private StringProperty phoneNumber;

    public PhoneDirectoryEntry(String lastName, String firstName, String phoneNumber) {
        StringBuilder num = new StringBuilder();
        for(int i=0; i<phoneNumber.length(); i++){
            if(Character.isDigit(phoneNumber.charAt(i)) ){
                num.append(phoneNumber.charAt(i));
            }
        }

        this.lastName = new SimpleStringProperty(this,"lastName",lastName);
        this.firstName = new SimpleStringProperty(this,"firstName",firstName);
        this.phoneNumber = new SimpleStringProperty(this,"phoneNumber", num.toString());
    }
    public StringProperty lastNameProperty() {
        return lastName;
    }
    public StringProperty firstNameProperty() {
        return firstName;
    }
    public StringProperty phoneNumberProperty(){
        return phoneNumber;
    }

}
