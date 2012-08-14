package br.com.mb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name="salvarMB")
public class SalvaArquivoMB implements Serializable{

	private static final long serialVersionUID = 5647900573295392345L;
	private String nome;
	private StreamedContent barcode;
	
	public StreamedContent getBarcode() {
		return barcode;
	}

	public void setBarcode(StreamedContent barcode) {
		this.barcode = barcode;
	}

	public CroppedImage getCroppedImage() {
		return croppedImage;
	}

	public void setCroppedImage(CroppedImage croppedImage) {
		this.croppedImage = croppedImage;
	}

	private CroppedImage croppedImage;
	//getter and setter
	public String crop() {
	ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
	String newFileName = servletContext.getRealPath("resources") + File.separator +
	"fotos" + File.separator+ "cropped.jpg";
	FileImageOutputStream imageOutput;
	try {
		File file = new File(servletContext.getRealPath("resources")+"temporario.tmp");
		file.createNewFile();
		FileInputStream imagem = new FileInputStream(file);
		imagem.read(croppedImage.getBytes());
		 StreamedContent content = new DefaultStreamedContent(imagem, "image/jpeg","myfotocropped");
		 imagem.close();
		 file.delete();
		 FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("myfotocropped", content );
	imageOutput = new FileImageOutputStream(new File(newFileName));
	imageOutput.write(croppedImage.getBytes(), 0,
	croppedImage.getBytes().length);
	imageOutput.close();
	croppedImage = null;
	} catch (Exception e) {
	e.printStackTrace();
	}
	return null;
	}

	public void handleFileUpload(FileUploadEvent event) {
			 try {
		/*	 DefaultStreamedContent dContent = new DefaultStreamedContent(event.getFile().getInputstream());*/
				 
			 //barcode = new event.getFile().getInputstream();
			 byte[] foto = event.getFile().getContents();
			 String[] caminho = event.getFile().getFileName().split("\\\\");
			 String valor = caminho[caminho.length-1];
			 caminho = valor.split("\\.");
			 FileOutputStream fileOutputStream = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources")+File.separator+"fotos"+File.separator+caminho[0]+".png"));
			 fileOutputStream.write(foto);
			 fileOutputStream.close();
			// barcode = new DefaultStreamedContent(new ByteArrayInputStream(event.getFile().getContents()),"image/jpeg"
			 //File barcodeFile = new File("file");
			// barcodeFile.
			 StreamedContent content = new DefaultStreamedContent(event.getFile().getInputstream(),"image/jpeg","myfoto");
			 FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("nome", caminho[0]+".png");
			 FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("myfoto", content );
			 FacesContext context =  FacesContext.getCurrentInstance();  
			 FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");  
			    FacesContext.getCurrentInstance().addMessage(null, msg); 
			    
			    this.nome = caminho[0];
			    
	 } catch (IOException ex) {
		 	ex.printStackTrace();
	 }
	 }

	public String getNome() {
		
			 	
		return this.nome+".png";
		
	}
	
	
}
