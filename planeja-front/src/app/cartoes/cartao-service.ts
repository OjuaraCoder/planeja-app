import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DadosCartaoForm, DetalhesCartao } from './dados-cartao';
import { Observable } from 'rxjs';
import { PageResult } from '../common/pagination/page-result';

@Injectable({
  providedIn: 'root',
})
export class CartaoService {
  private http = inject(HttpClient);
  baseUrl: string = 'http://localhost:8080/cartoes';

  criarCartao(dados: DadosCartaoForm): Observable<DetalhesCartao> {
    return this.http.post<DetalhesCartao>(this.baseUrl, dados);
  }

  listarCartoes(page: number = 0, size: number = 5): Observable<PageResult<DetalhesCartao>> {
    const url = `${this.baseUrl}?page=${page}&size=${size}`;
    return this.http.get<PageResult<DetalhesCartao>>(url);
  }

  obterPorId(id: string): Observable<DetalhesCartao> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<DetalhesCartao>(url);
  }

  atualizarCartao(id: string, dados: DadosCartaoForm): Observable<void> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.put<void>(url, dados);
  }

  mudarStatus(id: string):Observable<void>{
    const url = `${this.baseUrl}/${id}/status`;
    return this.http.patch<void>(url, {});
  }

}
