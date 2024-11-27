package src.livros;

public class LivrosException extends Exception {
   
        public LivrosException(Throwable th) { 
            super(th);
        }
        
        public LivrosException(String message, Throwable cause) {
            super(message, cause);
        }
    }

