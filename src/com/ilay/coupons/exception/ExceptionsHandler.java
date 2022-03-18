package com.ilay.coupons.exception;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.ilay.coupons.beans.ErrorBean;

@Provider
public class ExceptionsHandler implements ExceptionMapper<Throwable> 
{
	@Override
	public Response toResponse(Throwable error) 
	{
		if (error instanceof ApplicationException){
			ApplicationException e = (ApplicationException) error;

			int internalErrorCode = e.getErrorType().getNumber();
			String internalMessage = e.getMessage();									
			String externalMessage = e.getErrorType().toString();									
			ErrorBean errorBean = new ErrorBean(internalErrorCode, internalMessage, externalMessage);
			return Response.status(internalErrorCode).entity(errorBean).build();
		}

		String internalMessage = error.getMessage();
		ErrorBean errorBean = new ErrorBean(601, internalMessage, "General error");
		error.printStackTrace();
		return Response.status(601).entity(errorBean).build();
	}
}
