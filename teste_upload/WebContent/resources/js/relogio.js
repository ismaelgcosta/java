	

function tempo(){
	
	var data = new Date();
	
	var seg = data.getSeconds();
	var min = data.getMinutes();
	var hora = data.getHours();
		
 if((min > 0) || (seg > 0)){

	 			if(seg == 0){
                  seg = 59;
                    min = min - 1
                }
               else{
                   seg = seg - 1;                
                   
               }
 			
                if(min.toString().length == 1){
                    min = "0" + min;
                }
                if(seg.toString().length == 1){
                    seg = "0" + seg;
                    
                }
				if(hora.toString().length == 1){
                    hora = "0" + hora;
                }
				
				document.getElementById("ur").innerHTML = hora +":"+ min +":"+ seg;
                setTimeout('tempo()', 1000);	
                
            }

}
