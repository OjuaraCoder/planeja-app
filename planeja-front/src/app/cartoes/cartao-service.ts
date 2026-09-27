import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DadosCartaoForm, DetalhesCartao } from './dados-cartao';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CartaoService {
  private http = inject(HttpClient);

  criarCartao(dados: DadosCartaoForm): Observable<DetalhesCartao> {
    const url = `http://localhost:8080/cartoes`;
    return this.http.post<DetalhesCartao>(url, dados);
  }
}
