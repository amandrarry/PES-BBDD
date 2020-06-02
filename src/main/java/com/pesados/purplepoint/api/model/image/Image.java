package com.pesados.purplepoint.api.model.image;

import io.swagger.v3.oas.annotations.media.Schema;
import net.bytebuddy.utility.RandomString;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.Date;

@Entity
@Table(name = "Image")
public class Image {
	
	public static String defaultUrl = "https://image.flaticon.com/icons/svg/1738/1738691.svg";
	
	@Schema(description = "Id of the image.", required = true)
	@Id
	@Column(name = "imageid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long imageid;	
	@Schema(description = "Unique name of the image.", required = true)
	@Column(name = "imgname")
	private String imgname;	
	@Schema(description = "Type of the image.", required = false)
    @Column(name = "type")
	private String type;
	@Schema(description = "Image bytes.", required = true)
	@Column(name = "picByteB64")
	@Lob
	private String picByteB64;
	
	public Image() {
		super();
	}
	
	private String format(String name, String type) {
		int i = name.indexOf(".");
		if (i != -1) {
			name = name.substring(0, i) 
					+ "_" + RandomString.make() + new Date().getTime() 
					+ name.substring(i);
		} else {
			name = name	+ "_" + RandomString.make() + new Date().getTime();
			i = type.indexOf("/");
			if (i != -1) {
				name += "." + type.substring(i+1);
			} else {
				name += "." + type;
			}
		}
		return name;
	}
	
	
	public Image(String name, String type, String picByteB64) {
		// Ho fem una mica antibalas
		this.imgname = this.format(name, type) ;
		this.type = type;
		this.picByteB64 = picByteB64;
	}	
	
	public Image(String myUrl) throws IOException {
        URL imageUrl = new URL(myUrl);  

        // Get image from url
        
        BufferedImage image = ImageIO.read(imageUrl);

        // Get Byte Array
        
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
 
        // Write image as jpg into byteArray
        
        ImageIO.write(image,"svg",byteArrayOutputStream);

        // Flush image created to byteArrayOutoputStream

        byteArrayOutputStream.flush();

        // Create Random file name but unique by adding timestamp with extension

        this.imgname = RandomString.make() + new Date().getTime() + ".svg";
        this.picByteB64 = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());

        this.type = "image/svg";

        byteArrayOutputStream.close(); // Close once it is done saving
	}
	
	public void setImageid (Long id) {
		this.imageid = id;
	}
	
	public Long getImageid() {
		return this.imageid;
	}
	
	public void setName (String name) {
		this.imgname = name;
	}
	
	public String getName () {
		return this.imgname;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setPicByteB64(String data) {
		this.picByteB64 = data;
	}
	
	public String getPicByteB64() {
		return this.picByteB64;
	}
}
