package transformers;

import model.RestAPIException;
import spark.HaltException;
import spark.ResponseTransformer;

public class ExceptionTransformer implements ResponseTransformer {
    @Override
    public String render(Object model) {
//    	if(model instanceof HaltException){
//    		HaltException e = (HaltException) model;
//    		return "{\"error\" : \""+((HaltException) model).getMessage()+"\"}";
//    	}else if (model instanceof Exception){
//    		Exception e = (Exception) model;
//    		return "{\"error\" : \""+((Exception) model).getMessage()+"\"}";
//    	}else if (model instanceof RestAPIException){
//        	RestAPIException e = (RestAPIException) model;
//    		return "{\"error\" : \""+((RestAPIException) model).getMessage()+"\"}";
//    	}
    	return model.toString();
    }
}
