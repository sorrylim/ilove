//
//  ExtensionClass.swift
//  IOS
//
//  Created by 김세현 on 2020/07/02.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

extension UIApplication {
    func endEditing() {
        sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
}
