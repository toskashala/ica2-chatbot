(ns ghko.responses)
(def responses {:wc_yes {:1 "Yes there is a public WC at this park."
                         :2 "Yes, wc is available there."}
                :wc_no {:1 "Unfortunately, there is no WC at this park."
                        :2 "There is not any WC at this park."}
                :dogs_yes {:1 "Yes, you can walk your dog here."
                           :2 "Yes, walking the dog is allowed here."}
                :dogs_no {:1 "No, you cannot walk your dog here."
                          :2 "Sadly, you are not allowed to walk dogs at this park."}
                :biking_yes {:1 "You can ride a bike in this park."
                             :2 "Biking is allowed here."}
                :biking_no {:1 "No, you can't ride a bike in this park."
                            :2 "Riding a bike in this park is not allowed."}
                :attractions_yes {:1 ["You can see" , "."]}
                :transportation_yes {:1 ["You can get to the park using" , "."]}
                :skating_yes {:1 "Yes, skating is allowed there."}
                :skating_no {:1 "No, you cannot skate at this park."}
                :sports_yes {:1 "Yes, you can do sports there."}
                :sports_no {:1 "No, you can't do sports at this park."}
                :playground_yes {:1 "Yes, the park has a playground."
                                 :2 "Yes, a playground is located in this park."}
                :playground_no {:1 "No, this park does not have a playground."}
                :parking_yes {:1 "Yes, the park has parking areas"}
                :parking_no {:1 "No, the park does not have parking areas"}})