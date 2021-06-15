package br.com.zupacademy.autores;

import io.micronaut.core.annotation.Introspected
import java.util.function.Supplier
import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Singleton
class ExecutorTransacao(
	val manager: EntityManager
) {

	@Transactional
	 fun <T> removeEComita(objeto: T) {
		manager.remove(objeto);
	}
	
	@Transactional
	fun <T> salvaEComita(objeto: T): T {
		manager.persist(objeto);
		return objeto;
	}

	@Transactional
	fun <T> atualizaEComita(objeto: T): T {
		return manager.merge(objeto);
    }
    
    @Transactional
    fun <T> executa(algumCodigoComRetorno: Supplier<T>): T{
        return algumCodigoComRetorno.get();
    }
}