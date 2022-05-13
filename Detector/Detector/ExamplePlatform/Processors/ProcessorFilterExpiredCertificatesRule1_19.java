package Detector.ExamplePlatform.Processors;

import Detector.ExamplePlatform.Pojos.ServerCertificate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

public class ProcessorFilterExpiredCertificatesRule1_19 implements Predicate<ServerCertificate> {

    @Override
    public boolean test(ServerCertificate serverCertificate) {
        // As stated in CIS document, at rule 1.19 > Audit > From Command Line
        // Verify the ServerCertificateName and Expiration parameter value (expiration date) for
        // each SSL/TLS certificate returned by the list-server-certificates command and determine if
        // there are any expired server certificates currently stored in AWS IAM.
        //
        // In order to achieve this:
        // Extract the expiration date from the POJO and convert it to a LocalDateTime object.
        // Afterwards check if the expiration date is before the current date time. If so leave the
        // data in the stream (return true), else filter the data out (return false)

        String expirationDateAsString = serverCertificate.getExpiration();
        LocalDateTime expirationDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssz");
            expirationDate = LocalDateTime.parse(expirationDateAsString, formatter);
        }catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        return expirationDate.isBefore(LocalDateTime.now());

    }
}
