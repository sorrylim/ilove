//
//  LocationManager.swift
//  IOS
//
//  Created by 김세현 on 2020/07/21.
//  Copyright © 2020 KSH. All rights reserved.
//

import Foundation
import MapKit

class LocationManager : NSObject, ObservableObject {
    
    private let locationManager = CLLocationManager()
    @Published var location : CLLocation? = nil
    
    override init() {
        super.init()
        self.locationManager.delegate=self
        self.locationManager.desiredAccuracy = kCLLocationAccuracyBest
        self.locationManager.distanceFilter = kCLDistanceFilterNone
        self.locationManager.requestAlwaysAuthorization()
        self.locationManager.startUpdatingLocation()
    }
}

extension LocationManager : CLLocationManagerDelegate {
     
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        
        guard let location = locations.last else {
            return
        }
        
        self.location = location
        
    }
}
