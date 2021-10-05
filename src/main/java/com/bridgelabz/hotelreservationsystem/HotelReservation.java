package com.bridgelabz.hotelreservationsystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;



public class HotelReservation {
    Hotel hotel;
    ArrayList<Hotel> hotelList;
    CustomerType customer;

    public HotelReservation() {

        this.hotelList = new ArrayList<>();
        this.customer = CustomerType.REGULAR;
    }
    public void addHotel(String hotelName, int rating, int regularWeekDayRate, int regularWeekEndRate, int rewardWeekDayRate, int rewardWeekEndRate) {

    	 Map<CustomerType, Integer> weekDayRateMap = new HashMap<>();
         Map<CustomerType, Integer> weekEndRateMap = new HashMap<>();
         hotel = new Hotel(hotelName, rating, weekDayRateMap, weekEndRateMap);
         hotelList.add(hotel);
         weekDayRateMap.put(CustomerType.REGULAR, regularWeekDayRate);
         weekEndRateMap.put(CustomerType.REGULAR, regularWeekEndRate);

         weekDayRateMap.put(CustomerType.REWARD, rewardWeekDayRate);
         weekEndRateMap.put(CustomerType.REWARD, rewardWeekEndRate);
    }
    public int getSize(){
        return hotelList.size();
    }
    public int calculateTotalCostForGivenHotel(Hotel hotel, LocalDate startDate, LocalDate endDate) {

    	int numberOfDaysBetween = calculateTotalDays(startDate, endDate);
        int numberOfWeekDays = (int) calculateWeekDays(startDate, endDate);
        return hotel.getWeekDayRate(this.customer) * numberOfWeekDays + hotel.getWeekEndRate(this.customer) * (numberOfDaysBetween - numberOfWeekDays);
    }
    private int calculateWeekDays(LocalDate startDate, LocalDate endDate) {
		// TODO Auto-generated method stub
		return 0;
	}
	private int calculateTotalDays(LocalDate startDate, LocalDate endDate) {
		// TODO Auto-generated method stub
		return 0;
	}
	public ArrayList<Hotel> getCheapestHotelList(LocalDate startDate, LocalDate endDate) {

        Hotel hotel = hotelList.stream()
        		 .min(Comparator.comparing(hotel1 -> calculateTotalCostForGivenHotel(hotel1, startDate, endDate)))
                .orElse(null);
        ArrayList<Hotel> hotelList2 = new ArrayList<>();


        if (hotel != null) {
        	int cheapestPrice = calculateTotalCostForGivenHotel(hotel, startDate, endDate);
            for (Hotel newHotel : hotelList) {

                int hotelPrice = calculateTotalCostForGivenHotel(newHotel, startDate, endDate);
                if (hotelPrice == cheapestPrice) {

                    hotelList2.add(newHotel);
                    System.out.println("Hotel name: " + newHotel.getHotelName() + " \nCheapest price: " + cheapestPrice);
                }
            }
        }
        return hotelList2;
    }
    public Hotel getCheapestBestRatedHotel(LocalDate startDate, LocalDate endDate){
        ArrayList<Hotel> cheapestHotelList = getCheapestHotelList(startDate, endDate);
        int rate = 0;
        Hotel bestHotel = null;
        for (Hotel hotel: cheapestHotelList) {
            if(rate<hotel.getRating()){
                rate = hotel.getRating();
                bestHotel = hotel;
            }
        }
        return bestHotel;
    }
    public Hotel getBestRatedHotel(LocalDate startDate, LocalDate endDate){
        int rate = 0;
        Hotel bestHotel = null;
        for (Hotel hotel: hotelList) {
            if(rate<hotel.getRating()){
                rate = hotel.getRating();
                bestHotel = hotel;
            }
        }
        return bestHotel;
    }
    public void setCustomerType(CustomerType customerType){
    	if(customerType != null) {
            this.customer = customerType;
        }

    }
}
