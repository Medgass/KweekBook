import { useState } from 'react';
import { Mail, Lock, User } from 'lucide-react';
import { Button } from './ui/button';
import { Input } from './ui/input';
import { Card, CardContent, CardHeader } from './ui/card';
import { Label } from './ui/label';
import logo from 'figma:asset/c7a0325c9d08e8026b78e7ce8f58f07a64c11072.png';

interface LoginProps {
  onLogin: (userData: { name: string; email: string }) => void;
}

export function Login({ onLogin }: LoginProps) {
  const [isSignUp, setIsSignUp] = useState(false);
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    if (isSignUp) {
      // Sign up
      if (name && email && password) {
        onLogin({ name, email });
      }
    } else {
      // Login
      if (email && password) {
        // For demo purposes, use email as name if no stored name
        onLogin({ name: email.split('@')[0], email });
      }
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-background via-background to-secondary px-4">
      <Card className="w-full max-w-md border-border/50 shadow-xl">
        <CardHeader className="text-center space-y-4 pb-4">
          <div className="flex justify-center">
            <div className="relative">
              <div className="absolute inset-0 bg-gradient-to-r from-primary to-accent blur-2xl opacity-30 rounded-full animate-pulse"></div>
              <img src={logo} alt="KweekBook" className="w-32 h-32 relative object-contain" />
            </div>
          </div>
          <div>
            <h2 className="text-3xl bg-gradient-to-r from-primary to-accent bg-clip-text text-transparent">KweekBook</h2>
            <p className="text-muted-foreground">
              {isSignUp ? 'Créer un compte' : 'Connectez-vous à votre compte'}
            </p>
          </div>
        </CardHeader>
        
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-4">
            {isSignUp && (
              <div className="space-y-2">
                <Label htmlFor="name">Nom complet</Label>
                <div className="relative">
                  <User className="absolute left-3 top-1/2 transform -translate-y-1/2 text-muted-foreground h-5 w-5" />
                  <Input
                    id="name"
                    type="text"
                    placeholder="Jean Dupont"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    className="pl-10 focus:border-accent focus:ring-accent"
                    required={isSignUp}
                  />
                </div>
              </div>
            )}
            
            <div className="space-y-2">
              <Label htmlFor="email">Email</Label>
              <div className="relative">
                <Mail className="absolute left-3 top-1/2 transform -translate-y-1/2 text-muted-foreground h-5 w-5" />
                <Input
                  id="email"
                  type="email"
                  placeholder="exemple@email.com"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  className="pl-10 focus:border-accent focus:ring-accent"
                  required
                />
              </div>
            </div>
            
            <div className="space-y-2">
              <Label htmlFor="password">Mot de passe</Label>
              <div className="relative">
                <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 text-muted-foreground h-5 w-5" />
                <Input
                  id="password"
                  type="password"
                  placeholder="••••••••"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  className="pl-10 focus:border-accent focus:ring-accent"
                  required
                />
              </div>
            </div>

            <Button 
              type="submit"
              className="w-full bg-gradient-to-r from-primary to-accent hover:from-primary/90 hover:to-accent/90 text-white shadow-lg shadow-accent/20 hover:shadow-xl hover:shadow-accent/30"
            >
              {isSignUp ? "S'inscrire" : 'Se connecter'}
            </Button>
          </form>

          <div className="mt-6 text-center">
            <button
              type="button"
              onClick={() => setIsSignUp(!isSignUp)}
              className="text-sm text-muted-foreground hover:text-accent transition-colors"
            >
              {isSignUp ? (
                <>Vous avez déjà un compte ? <span className="text-accent font-medium">Se connecter</span></>
              ) : (
                <>Pas encore de compte ? <span className="text-accent font-medium">S'inscrire</span></>
              )}
            </button>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}