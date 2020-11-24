import java.util.Random;

public class RSA {

    public int e;
    public int n;
    public int d;

    public RSA(int p, int q){
        llaves(p,q);
    }

    public String cifrar(String mensaje, int e, int n){
        String res="";
        for(int i=0;i<mensaje.length();i++){
            char act=mensaje.charAt(i);
            int cif=expModulo(act,e,n);
            res+=(char)cif+"";
        }
        return res;
    }

    public String decifrar(String mensaje, int d, int n){
        String res="";
        for(int i=0;i<mensaje.length();i++){
            char act=mensaje.charAt(i);
            int decif=expModulo(act,d,n);
            res+=(char) decif+"";
        }
        return res;
    }

    int expModulo(int a,int pow,int mod) {
        long x=1;
        long y=a;
        while(pow > 0){
            if(pow%2 == 1){
                x=(x*y)%mod;
            }
            y = (y*y)%mod;
            pow /= 2;
        }
        return (int) x%mod;
    }

    public void llaves(int p, int q){

        int n=p*q;
        int fiDeN=(p-1)*(q-1);
        Random ran = new Random();
        int e=ran.nextInt(fiDeN-2)+2;
        int d = obtenerInverso(e,fiDeN);
        while (!sonCoprimos(e,fiDeN)||d<0){
            e=ran.nextInt(fiDeN-2)+2;
            d=obtenerInverso(e,fiDeN);
        }
        this.n=n;
        this.e=e;
        this.d=d;

    }

    public boolean sonCoprimos(int a, int b){
        return MCD(a,b)==1;
    }

    //buscado y sacado de:
    // https://es.wikipedia.org/wiki/Inverso_multiplicativo_(aritm%C3%A9tica_modular)#:~:text=En%20la%20aritm%C3%A9tica%20modular%2C%20el,%E2%89%A1%20m%20(mod%20p).
    public int obtenerInverso(int a,int m)
    {
        int c1 = 1;
        int c2 = -(m/a); //coeficiente de a y b respectivamente
        int t1 = 0;
        int t2 = 1; //coeficientes penultima corrida
        int r = m % a; //residuo, asignamos 1 como condicion de entrada
        int x=a,y=r,c;
        while(r!=0)
        {
            c = x/y;//cociente
            r = x%y;//residuo
            //guardamos valores temporales de los coeficientes
            //multiplicamos los coeficiente por -1*cociente de la division
            c1*=-c;
            c2*=-c;
            //sumamos la corrida anterior
            c1+=t1;
            c2+=t2;
            //actualizamos corrida anterior
            t1=-(c1-t1)/c;
            t2=-(c2-t2)/c;
            x=y;
            y=r;
        }
        if(x==1) {//residuo anterior es 1 , son primos relativos y el inverso existe
            return t2;
        }
        else
           return -1;
    }

    public int MCD(int a, int b){
        while(a != b)
            if(a>b)
                a= a-b;
            else
                b= b -a;
        return a;
    }

    public static void main(String[] args) {
        RSA rsa=new RSA(101,103);
        String mesg="hola";
        String mesgCifrado=rsa.cifrar(mesg,rsa.e,rsa.n);
        String mesgDecifrado=rsa.decifrar(mesgCifrado,rsa.d,rsa.n);
        System.out.println(rsa.expModulo(2,10,20));
        System.out.println("Mensaje original: "+mesg);
        System.out.println("Mensaje cifrado: "+mesgCifrado);
        System.out.println("Mensaje descifrado: "+mesgDecifrado);
    }
}
