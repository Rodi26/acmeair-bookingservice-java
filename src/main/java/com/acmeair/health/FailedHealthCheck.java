package com.acmeair.health;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import com.acmeair.web.HealthCheckRest;

/**
 * 
 * @author jagraj
 *
 */
@Health
@ApplicationScoped
public class FailedHealthCheck implements HealthCheck{
	
	@Inject HealthCheckBean healthCheckBean;
	@Inject
	private HealthCheckRest healthCheckRest;
	
    @Override
    public HealthCheckResponse call() {
        
		try {
			if(healthCheckRest.checkStatus().getStatus()!=200) {
				return HealthCheckResponse.named("BookingService:failed-check").down().build();
			}
			else if(healthCheckBean.getIsAppDown()!=null && healthCheckBean.getIsAppDown().booleanValue()==true) {
				return HealthCheckResponse.named("BookingService:failed-check").down().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return HealthCheckResponse.named("BookingService:successful-check").up().build();
	}

        

}