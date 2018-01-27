/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.Bid;
import java.math.BigDecimal;
import util.exception.InvalidBidException;

/**
 *
 * @author josh
 */
public interface BidControllerRemote {

    public BigDecimal getBidIncrement(BigDecimal currentPrice);

    public void placeBid(Bid bid) throws InvalidBidException;
}
