package Detector.ExamplePlatform.Processors;

import Detector.Alert;
import Detector.ExamplePlatform.Pojos.UserClassExample;

import java.util.function.Function;

public class ProcessorGenerateAlert implements Function<UserClassExample, Alert> {


    @Override
    public Alert apply(UserClassExample userClassExample) {

        // The remaining data has to be packed into the Alert class
        Alert result = new Alert(
                "EXAMPLE RULE 0.0",
                "Raise an alarm if two users with identical user name exist on the platform"
        );
        // Add relevant information to the alert in order for the user to understand what User is affected
        result.ExtraInfo.put("Duplicate User ARN", userClassExample.getArn());
        return result;
    }


}
