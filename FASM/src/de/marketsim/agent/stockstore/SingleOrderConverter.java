package de.marketsim.agent.stockstore;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import de.marketsim.util.SingleOrder;
import de.marketsim.message.SimpleSingleOrder;
import de.marketsim.message.AktienTrade_Order;
import de.marketsim.message.CashTrade_Order;
import de.marketsim.SystemConstant;
import jade.core.AID;

public class SingleOrderConverter
{

  public static SingleOrder[] getSingleOrder(AktienTrade_Order  pAktienTrade_Order, AID pSenderAID)
  {
          // 2007-12-09
          // 2007-12-12: Check if 8 Orders of Investor
          // Neue Idee von Oliver
          if ( pAktienTrade_Order.containsExtendedOrder() )
          {
              SimpleSingleOrder[] buyorders  =  pAktienTrade_Order.getBuyOrders();
              SimpleSingleOrder[] sellorders =  pAktienTrade_Order.getSellOrders();

              SingleOrder[] orders_for_calculator = new SingleOrder[ buyorders.length + sellorders.length];
              int j =0;
              // Zuerst alle Buy Orders
              for ( int i=0; i < buyorders.length; i++ )
              {
                orders_for_calculator[j] =
                new SingleOrder(pSenderAID,
                                SystemConstant.WishType_Buy,
                                pAktienTrade_Order.mAgentType,
                                buyorders[i].mMenge,
                                buyorders[i].mLimit,
                                pAktienTrade_Order.mLimitCalcBase,
                                pAktienTrade_Order.mOriginalAgentType);
                orders_for_calculator[j].mOrderOfFundamentalInvestor = true;
                orders_for_calculator[j].mOrderInternalNo = i;
                j = j + 1;
              }

              // dann alle Sell Orders
              for ( int i=0; i < sellorders.length; i++ )
              {
                orders_for_calculator[j] =
                new SingleOrder(pSenderAID,
                                SystemConstant.WishType_Sell,
                                pAktienTrade_Order.mAgentType,
                                sellorders[i].mMenge,
                                sellorders[i].mLimit,
                                pAktienTrade_Order.mLimitCalcBase,
                                pAktienTrade_Order.mOriginalAgentType);
                orders_for_calculator[j].mOrderOfFundamentalInvestor = true;
                orders_for_calculator[j].mOrderInternalNo = i;
                j = j + 1;
              }
              return orders_for_calculator;
          }

          if ( pAktienTrade_Order.mOrderWish == SystemConstant.WishType_Mixed )
          {
                   SingleOrder[] orders = new SingleOrder[2];
                   orders[0] =  new SingleOrder(
                                       pSenderAID,
                                       SystemConstant.WishType_Buy,
                                       pAktienTrade_Order.mAgentType,
                                       pAktienTrade_Order.mBuyMenge,
                                       pAktienTrade_Order.mBuyLimit,
                                       pAktienTrade_Order.mLimitCalcBase,
                                       pAktienTrade_Order.mOriginalAgentType  ) ;
                   orders[1] =  new SingleOrder(
                                       pSenderAID,
                                       SystemConstant.WishType_Sell,
                                       pAktienTrade_Order.mAgentType,
                                       pAktienTrade_Order.mSellMenge,
                                       pAktienTrade_Order.mSellLimit,
                                       pAktienTrade_Order.mLimitCalcBase,
                                       pAktienTrade_Order.mOriginalAgentType ) ;
                   return orders;
          }
          else
          {
                if ( pAktienTrade_Order.mOrderWish == SystemConstant.WishType_Buy )
                {
                    // buy
                    SingleOrder[] orders = new SingleOrder[1];
                    orders[0] =  new SingleOrder(
                                        pSenderAID,
                                        pAktienTrade_Order.mOrderWish,
                                        pAktienTrade_Order.mAgentType,
                                        pAktienTrade_Order.mBuyMenge,
                                        pAktienTrade_Order.mBuyLimit,
                                        pAktienTrade_Order.mLimitCalcBase,
                                        pAktienTrade_Order.mOriginalAgentType) ;
                    return orders;
                }
                else
                if ( pAktienTrade_Order.mOrderWish == SystemConstant.WishType_Sell )
                {
                    // sell
                    SingleOrder[] orders = new SingleOrder[1];
                    orders[0] =  new SingleOrder(
                                        pSenderAID,
                                        pAktienTrade_Order.mOrderWish,
                                        pAktienTrade_Order.mAgentType,
                                        pAktienTrade_Order.mSellMenge,
                                        pAktienTrade_Order.mSellLimit,
                                        pAktienTrade_Order.mLimitCalcBase,
                                        pAktienTrade_Order.mOriginalAgentType ) ;
                    return orders;
                }
                else
                {
                    // Wait
                    SingleOrder[] orders = new SingleOrder[1];
                    orders[0] =  new SingleOrder(
                                        pSenderAID,
                                        pAktienTrade_Order.mOrderWish,
                                        pAktienTrade_Order.mAgentType,
                                        0,
                                        0,
                                        "",
                                        pAktienTrade_Order.mOriginalAgentType  ) ;
                    return orders;
                }
          }
  }

  public static SingleOrder[] getSingleOrder(CashTrade_Order  pCashTrade_Order, AID pSenderAID)
  {
    if ( pCashTrade_Order.mAgentType == SystemConstant.AgentType_INVESTOR )
    {
         if ( pCashTrade_Order.mOrderWish == SystemConstant.WishType_Mixed )
         {
               SingleOrder[] orders = new SingleOrder[2];
               orders[0] =  new SingleOrder(
                              pSenderAID,
                              SystemConstant.WishType_Buy,
                              pCashTrade_Order.mAgentType,
                              pCashTrade_Order.mCash2,
                              pCashTrade_Order.mBuyLimit,
                              pCashTrade_Order.mLimitCalcBase,
                              pCashTrade_Order.mAgentType) ;

               /// to be changed !!!!!!!!!!!!!!

               orders[1] =  new SingleOrder(
                              pSenderAID,
                              SystemConstant.WishType_Sell,
                              pCashTrade_Order.mAgentType,
                              pCashTrade_Order.mCash2,
                              pCashTrade_Order.mSellLimit,
                              pCashTrade_Order.mLimitCalcBase,
                              pCashTrade_Order.mAgentType ) ;
               return orders;
         }
         else
         {
               SingleOrder[] orders = new SingleOrder[1];
               orders[0] =  new SingleOrder(
                              pSenderAID,
                              pCashTrade_Order.mOrderWish,
                              pCashTrade_Order.mAgentType,
                              0,
                              0,
                              "",
                              pCashTrade_Order.mAgentType ) ;
               return orders;
         }
    }
    else
    {
            if ( pCashTrade_Order.mOrderWish == SystemConstant.WishType_Buy )
            {
                // buy
                SingleOrder[] orders = new SingleOrder[1];
                orders[0] =  new SingleOrder(
                               pSenderAID,
                               pCashTrade_Order.mOrderWish,
                               pCashTrade_Order.mAgentType,
                               pCashTrade_Order.mCash2,
                               pCashTrade_Order.mBuyLimit,
                               pCashTrade_Order.mLimitCalcBase,
                               pCashTrade_Order.mAgentType ) ;
                return orders;
            }
            else
            if ( pCashTrade_Order.mOrderWish == SystemConstant.WishType_Sell )
            {
                // sell
                SingleOrder[] orders = new SingleOrder[1];
                orders[0] =  new SingleOrder(
                               pSenderAID,
                               pCashTrade_Order.mOrderWish,
                               pCashTrade_Order.mAgentType,
                               pCashTrade_Order.mCash2,
                               pCashTrade_Order.mSellLimit,
                               pCashTrade_Order.mLimitCalcBase,
                               pCashTrade_Order.mAgentType ) ;
                return orders;
            }
            else
            {
                // wait
                SingleOrder[] orders = new SingleOrder[1];
                orders[0] =  new SingleOrder(
                               pSenderAID,
                               pCashTrade_Order.mOrderWish,
                               pCashTrade_Order.mAgentType,
                               0,
                               0,
                               "",
                               pCashTrade_Order.mAgentType ) ;
                return orders;
            }
    }
  }

}

