/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Objects.TimerObject;
import java.util.Calendar;
import java.util.Collection;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.NoSuchObjectLocalException;
import javax.ejb.Remote;
import javax.ejb.ScheduleExpression;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

/**
 *
 * @author josh
 */
@Stateless
@Local(TimerControllerLocal.class)
@Remote(TimerControllerRemote.class)
public class TimerController implements TimerControllerRemote, TimerControllerLocal {

    @Resource
    private SessionContext sessionContext;
    @EJB
    private AuctionListingControllerLocal auctionListingControllerLocal;

    public TimerController() {
    }

    @Override
    public void startTimer(Long auctionListingID, Calendar date, String type) {
        TimerService timerService = sessionContext.getTimerService();
        ScheduleExpression schedule = new ScheduleExpression();
        schedule.year(date.get(Calendar.YEAR)).month(date.get(Calendar.MONTH) + 1).dayOfMonth(date.get(Calendar.DATE));
        schedule.hour(date.get(Calendar.HOUR_OF_DAY)).minute(date.get(Calendar.MINUTE)).second(date.get(Calendar.SECOND));
        timerService.createCalendarTimer(schedule, new TimerConfig(new TimerObject(auctionListingID, type), true));
    }

    @Override
    public void updateTimer(Long auctionListingID, Calendar date, String type) {
        TimerService timerService = sessionContext.getTimerService();
        Collection<Timer> timers = timerService.getTimers();
        timers.forEach((timer) -> {
            try {
                TimerObject timerObject = (TimerObject) timer.getInfo();
                if (timerObject.getAuctionListingID().equals(auctionListingID) && timerObject.getType().equals(type)) {
                    timer.cancel();
                }
                startTimer(auctionListingID, date, type);
            } catch (NoSuchObjectLocalException ex) {
                System.out.println(ex.getMessage());
            }
        });
    }

    @Timeout
    public void handleTimeout(Timer timer) {
        TimerObject timerObject = (TimerObject) timer.getInfo();
        if (timerObject.getType().equals("openListing")) {
            System.out.println("Timeout!");
            auctionListingControllerLocal.openAuctionListing(timerObject.getAuctionListingID());
        } else if (timerObject.getType().equals("closeListing")) {
            auctionListingControllerLocal.closeAuctionListing(timerObject.getAuctionListingID());
        }

    }
}
