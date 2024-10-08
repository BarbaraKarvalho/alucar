package com.barbara.alucar.application.service;

import com.barbara.alucar.domain.entity.Invoice;
import com.barbara.alucar.domain.entity.Rental;
import com.barbara.alucar.infrastructure.repository.InvoiceRepository;
import com.barbara.alucar.infrastructure.repository.RentalRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;

import static com.barbara.alucar.utils.Constraints.DAY_TO_HOURS;
import static com.barbara.alucar.utils.Constraints.HOUR_TO_MINUTES;

@ApplicationScoped
public class InvoiceService {

    @Inject
    private InvoiceRepository invoiceRepository;

    @Inject
    private TaxService taxService;

    @Inject
    private RentalRepository rentalRepository;

    @ConfigProperty(name="rental.price-per-hour")
    private double pricePerHour;

    @ConfigProperty(name="rental.price-per-day")
    private double pricePerDay;

    public void process(Rental rental) {
    //TODO: Até 12 hrs - cobrar por hora / + de 12hrs cobrar diária
        //FIXME: Considerar tbm o valor da diária
        Duration duration = Duration.between(rental.getStart(), rental.getFinish()); //is object = variable of class
        int hoursDuration = (int) Math.ceil(duration.toMinutes()/HOUR_TO_MINUTES);
        int daysDuration = (int) Math.ceil(duration.toHours()/DAY_TO_HOURS);
        double basicPayment;

        if(hoursDuration > 12) //regra if sem chaves com uma linha
            basicPayment = pricePerDay * daysDuration;
        else
            basicPayment = pricePerHour * hoursDuration;

        double tax = taxService.calculate(basicPayment);


        Invoice invoice = Invoice.builder() //is object
                .basicPayment(basicPayment) //is set again
                .tax(tax)
                .build();
        System.out.println(invoice);
        invoiceRepository.persist(invoice);
        System.out.println(invoice);

        rental.setInvoiceId(invoice.id); // linquei alguel com recibo
        rentalRepository.update(rental);

    }
}
