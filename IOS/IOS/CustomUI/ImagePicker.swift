//
//  ImagePicker.swift
//  IOS
//
//  Created by 김세현 on 2020/07/02.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import PhotosUI

struct ImagePicker : UIViewControllerRepresentable{
    
    @Environment(\.presentationMode) var presentationMode
    @Binding var image:UIImage?
    @Binding var imageUrl:URL?
    
    
    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }
    
    func makeUIViewController(context : UIViewControllerRepresentableContext<ImagePicker>) -> UIImagePickerController {
        let picker = UIImagePickerController()
        picker.delegate = context.coordinator
        return picker
    }
    
    func updateUIViewController(_ uiViewController: UIImagePickerController, context: UIViewControllerRepresentableContext<ImagePicker>) {

    }
    
    class Coordinator: NSObject, UINavigationControllerDelegate, UIImagePickerControllerDelegate {
        let parent: ImagePicker

        init(_ parent: ImagePicker) {
            self.parent = parent
        }
        
        func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey: Any]) {
            if let uiImage = info[.originalImage] as? UIImage {
                parent.image = uiImage
            }
            if let imageUrl = info[.imageURL] as? URL{
                parent.imageUrl = imageUrl
            }

            parent.presentationMode.wrappedValue.dismiss()
        }
    }
}


