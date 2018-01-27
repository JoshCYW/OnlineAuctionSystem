/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author josh
 */
public class TimerObject implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timerID;
    private Long auctionListingID;
    private String type;

    public TimerObject() {
    }

    public TimerObject(Long auctionID, String type) {
        this.auctionListingID = auctionID;
        this.type = type;
    }

    public Long getTimerID() {
        return timerID;
    }

    public Long getAuctionListingID() {
        return auctionListingID;
    }

    public String getType() {
        return type;
    }

    public void setTimerID(Long timerID) {
        this.timerID = timerID;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TimerObject other = (TimerObject) obj;
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.timerID, other.timerID)) {
            return false;
        }
        if (!Objects.equals(this.auctionListingID, other.auctionListingID)) {
            return false;
        }
        return true;
    }
    
    
}
