import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HTTP_INTERCEPTORS } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Route } from "@angular/router";
import { Observable } from "rxjs";
import { JwttokenService } from "./jwttoken.service";


const TOKEN_HEADER_KEY= "Authorization"

@Injectable()
export class AuthInterceptor implements HttpInterceptor{

    constructor(private token: JwttokenService){}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        console.log(" Intercepting Request: "+req)
        let authRequest = req
        const loginPath = "/login"
        const token = this.token.getTokenFromSession()
        console.log("Your Token is: "+token)
        if(token!=null){
            authRequest = req.clone({headers:req.headers.set(TOKEN_HEADER_KEY,'Bearer '+token)})
            console.log("auth request: "+authRequest.url+" auth request whole: "+authRequest.headers.get(TOKEN_HEADER_KEY))
        }
        return next.handle(authRequest)
    }

}

export const authInterceptorProviders=[
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor,multi: true}
]