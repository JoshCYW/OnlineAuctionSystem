/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import java.util.Calendar;
import javax.ejb.Timer;

/**
 *
 * @author josh
 */
public interface TimerControllerRemote {

    public void startTimer(Long auctionListingID, Calendar date, String type);

    public void updateTimer(Long auctionListingID, Calendar date, String type);

    public void handleTimeout(Timer timer);
    
}
