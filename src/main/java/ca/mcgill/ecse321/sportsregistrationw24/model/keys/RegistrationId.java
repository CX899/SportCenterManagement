package ca.mcgill.ecse321.sportsregistrationw24.model.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RegistrationId implements Serializable {
    @Column(name = "customer_account_id")
    private Integer customerAccountId;
    @Column(name = "course_offering_id")
    private Integer courseOfferingId;

    public RegistrationId() {}

    public RegistrationId(Integer customerAccountId, Integer courseOfferingId) {
        this.customerAccountId = customerAccountId;
        this.courseOfferingId = courseOfferingId;
    }

    public Integer getCustomerAccountId() {
        return customerAccountId;
    }

    public void setCustomerAccountId(Integer customerAccountId) {
        this.customerAccountId = customerAccountId;
    }

    public Integer getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(Integer courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }
}
